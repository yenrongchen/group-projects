from django.shortcuts import render
from django.db.models import Count
from django.db.models.functions import ExtractWeek, ExtractMonth, ExtractYear, TruncDate
from django.utils.timezone import localtime
from .models import AttackType, Tips, SummaryHistory, QAHistory, ClassificationHistory
import requests
import json
import pandas as pd
import matplotlib
import matplotlib.pyplot as plt
import io
import base64
from datetime import datetime, timedelta

def classification_view(request):
    matplotlib.use('Agg') 
        
    attack_type = None
    description = None
    chart_data = None
    type_percentage = None
    log_input = ""

    if request.method == 'POST':
        log_input = request.POST.get('log_input', '')
        
        if not log_input.startswith('{'):
            log_input = '{' + log_input
        if not log_input.endswith('}'):
            log_input = log_input + '}'
        
        try:
            log_input = json.loads(log_input)
        except json.JSONDecodeError:
            return render(request, 'ask_me/classification.html', {
                'attack_type': "Error: Invalid input format.",
                'description': "Please provide a valid JSON-formatted cowrie log.",
                'log_input': log_input
            })
            
        if not log_input:
            return render(request, 'ask_me/classification.html', {
                'attack_type': "Error: No input provided.",
                'description': "Please paste a valid cowrie log row.",
                'log_input': log_input
            })

        backend_url = "https://ewe-happy-centrally.ngrok-free.app/classify"  
        response = requests.post(backend_url, json=log_input)
        
        if response.status_code == 200:
            result = response.json()
            
            if result["status"] == "success":
                attack_type = result.get('attack_type')
                probability = result.get('probabilities')

                if request.user.is_authenticated:
                    record = ClassificationHistory(
                        user=request.user,
                        input_log=json.dumps(log_input), 
                        attack_type=attack_type, 
                        # actual_type=log_input['eventid'],
                        probability=json.dumps(probability)
                    )
                    record.save()

                try:
                    attack_type_entry = AttackType.objects.get(attack_type=attack_type)
                    description = attack_type_entry.description
                except AttackType.DoesNotExist:
                    description = "No description available for this attack type."

            # backend exception        
            else:
                print("backend exception:", result["attack_type"])
                return render(request, 'ask_me/classification.html', {
                    'error': "An unexpected error occurred. Please try again later."
                })
        else:
            attack_type = "Error retrieving attack type from backend."
            description = "Please check the input or try again later."

        type_data = ClassificationHistory.objects.values('attack_type') \
        .annotate(count=Count('attack_type'))
        
        type_counts = pd.Series({item['attack_type']: item['count'] for item in type_data}).sort_values(ascending=False)
        type_percentage = round((type_counts[attack_type] / type_counts.sum()) * 100, 2)
        try:
            fig, ax = plt.subplots(figsize=(10, 6))
            type_counts.plot(kind='bar', ax=ax, color='skyblue', alpha=0.7)
            for i, v in enumerate(type_counts):
                ax.text(i, v + 0.5, str(v), ha='center')

            if attack_type in type_counts.index:
                idx = type_counts.index.tolist().index(attack_type)
                ax.patches[idx].set_facecolor('navy')

            ax.set_xticklabels(type_counts.index, rotation=45, ha='right')  
            ax.set_xlabel("Attack Types")  
            ax.set_ylabel("Number of Occurrences")  
            ax.set_title("Proportion of Attack Types From History")  
            plt.tight_layout()

            buffer = io.BytesIO()
            plt.savefig(buffer, format='png')
            buffer.seek(0)
            image_png = buffer.getvalue()
            buffer.close()
            chart_data = base64.b64encode(image_png).decode('utf-8')
        except FileNotFoundError:
            description = "Error: CSV file not found. Please contact the admin."
        except Exception as e:
            description = f"Error processing the CSV file: {str(e)}"

    return render(request, 'ask_me/classification.html', {
        'attack_type': attack_type,
        'attack_percentage': type_percentage,
        'description': description,
        'chart_data': chart_data,
        'log_input': log_input
    })


def qa_view(request):
    answer = None
    question = None
    
    tips = list(Tips.objects.all())
    tips_data = [{'content': tip.content} for tip in tips] 
    
    if request.method == 'POST':
        question = request.POST.get('question')
        backend_url = "https://ewe-happy-centrally.ngrok-free.app/qa"

        try:
            response = requests.post(backend_url, json={'question': question})

            if response.status_code == 200:
                answer = response.json().get('answer')

                # 避免出現斷尾文字
                if not answer.endswith("."):
                    answer = " ".join(answer.split(".")[:-1])
                    answer = answer.strip()

                    if answer[-2].isdigit():
                        answer = answer[:-2].strip()

                if request.user.is_authenticated:
                    record = QAHistory(user=request.user, question=question, answer=answer)
                    record.save()

        except Exception as e:
            print(f"Exception occurred: {e}")
            answer = "An error occurred while generating the answer. Please try again."

    return render(request, 'ask_me/qa.html', {
        'answer': answer, 
        'question': question, 
        'tips': json.dumps(tips_data)  
    })


def summary_view(request):
    summary = None
    paragraph = None

    if request.method == 'POST':
        paragraph = request.POST.get('paragraph')

        backend_url = "https://ewe-happy-centrally.ngrok-free.app/summarize"  
        
        try:
            response = requests.post(backend_url, json={'paragraph': paragraph})
            response.raise_for_status()
            summary = response.json().get('summary')

            if not summary.endswith("."):
                summary = summary + "."

            if request.user.is_authenticated:
                record = SummaryHistory(user=request.user, paragraph=paragraph, summary=summary)
                record.save()
            
        except requests.RequestException as e:
            print(f"Request failed: {e}")
            summary = "An error occurred while generating the summary. Please try again."

    return render(request, 'ask_me/summary.html', {'paragraph': paragraph, 'summary': summary})


def cHistory_view(request):
    attackType = [
        'cowrie.session.connect',
        'cowrie.client.version',
        'cowrie.client.kex',
        'cowrie.login.failed',
        'cowrie.session.closed',
        'cowrie.login.success',
        'cowrie.session.params',
        'cowrie.command.input',
        'cowrie.session.file_download',
        'cowrie.direct-tcpip.request',
        'cowrie.direct-tcpip.data',
        'cowrie.log.closed',
        'cowrie.command.failed',
        'cowrie.client.size',
        'cowrie.session.file_upload',
        'cowrie.session.file_download.failed'
    ]

    frequency = []
    attack_data = []

    for atkType in attackType:
        # bar chart data
        freq = ClassificationHistory.objects \
        .filter(user=request.user) \
        .filter(attack_type=atkType) \
        .count()

        frequency.append(freq)

        # attack type table data
        if freq != 0:
            records = ClassificationHistory.objects \
            .filter(user=request.user) \
            .filter(attack_type=atkType) \
            .values('id', 'timestamp', 'input_log') \
            .order_by('-timestamp')  # DESC order

            records_list = [{
                'id': record['id'], 
                'time': localtime(record['timestamp']).strftime("%Y.%m.%d  %I:%M %p"), 
                'input_log': record['input_log']
            } for record in records]

            attack_data.append({
                "attack_type": atkType,
                "count": freq,
                "records": records_list
            })

    # time table data
    timeset = ClassificationHistory.objects \
    .filter(user=request.user) \
    .values('id', 'timestamp', 'input_log', 'attack_type') \
    .annotate(count=Count('id')) \
    .order_by('-timestamp')
    
    time_data = [{
        'id': entry['id'], 
        'time': localtime(entry['timestamp']).strftime("%Y.%m.%d  %I:%M %p"), 
        'input_log': entry['input_log'],
        'attack_type': entry['attack_type'],
        'count': entry['count']
    } for entry in timeset]
    
    # Order by month
    month_logs = ClassificationHistory.objects\
    .filter(user=request.user)\
    .annotate(
        year=ExtractYear('timestamp'),
        month=ExtractMonth('timestamp')
    ).values('year', 'month', 'id', 'input_log', 'timestamp', 'attack_type')
    
    month_result = {}
    
    for log in month_logs:
        month = f"{log['year']}-{log['month']:02d}"

        if month not in month_result:
            month_result[month] = {
                'count': 0,
                'record': []
            }

        month_result[month]['count'] += 1
        month_result[month]['record'].append({
            'time': localtime(log['timestamp']).strftime("%Y.%m.%d  %I:%M %p"),
            'input_log': log['input_log'],
            'attack_type': log['attack_type'],
            'id': log['id']
        })
    
    month_data = [
    {
        "month": month,  
        "count": data['count'],  
        "record": data['record']  
    }
    for month, data in month_result.items()
    ]
    
    # Order by week
    week_logs = ClassificationHistory.objects\
    .filter(user=request.user)\
    .annotate(
        year=ExtractYear('timestamp'),
        week=ExtractWeek('timestamp')
    ).values('year', 'week', 'id', 'input_log', 'timestamp', 'attack_type')
    
    week_result = {}
    
    for log in week_logs:
        year, week = log['year'], log['week']
        week_start = datetime.strptime(f'{year}-W{week - 1}-1', "%Y-W%U-%w").date()
        
        if week_start not in week_result:
            week_result[week_start] = {
                'count': 0,
                'record': []
            }
        
        week_result[week_start]['count'] += 1
        week_result[week_start]['record'].append({
            'time': localtime(log['timestamp']).strftime("%Y.%m.%d  %I:%M %p"),
            'input_log': log['input_log'],
            'attack_type': log['attack_type'],
            'id': log['id']
        })
    
    week_data = [
    {
        "week": week_start.strftime("%Y-%m-%d"),
        "count": data['count'],
        "record": data['record']
    }
        for week_start, data in week_result.items()
    ]
    
    # Order by day
    day_logs = ClassificationHistory.objects\
    .filter(user=request.user)\
    .annotate(
        date=TruncDate('timestamp')  
    ).values('date', 'id', 'input_log', 'timestamp', 'attack_type')
    
    result = {}
    for log in day_logs:
        day_key = log['date'].strftime("%Y-%m-%d")
        
        if day_key not in result:
            result[day_key] = {
                'count': 0,
                'record': []
        }
            
        result[day_key]['count'] += 1
        result[day_key]['record'].append({
            'time': localtime(log['timestamp']).strftime("%Y.%m.%d  %I:%M %p"),
            'input_log': log['input_log'],
            'attack_type': log['attack_type'],
            'id': log['id'],
        })
        
    day_data = [
    {
        "date": day_key, 
        "count": data['count'],  
        "record": data['record']  
    }
        for day_key, data in result.items()
    ]
    
    heat_map_data = [
        {"date": day["date"], "count": day["count"]} 
        for day in day_data
    ]
        
    return render(request, 'ask_me/cHistory.html', {
        'attackType': attackType,
        'frequency': frequency,
        'attack_data': attack_data,
        'time_data': time_data,
        'month_data': month_data,
        'week_data': week_data,
        'day_data': day_data,
        'heat_map_data': heat_map_data
    })


def detail_view(request):
    id = request.GET.get('id')

    entry = ClassificationHistory.objects.filter(user=request.user, id=id) \
    .values('probability', 'timestamp', 'attack_type', 'input_log').first()
    
    attack_type_count = ClassificationHistory.objects.filter(attack_type=entry["attack_type"]).count()
    total = ClassificationHistory.objects.count()
    attack_percentage = round((attack_type_count * 100 / total), 2)

    probs = list(eval(entry['probability'][1:-1]))
    time = localtime(entry['timestamp']).strftime("%Y.%m.%d  %I:%M %p")

    categories = [
        'cowrie.session.connect',
        'cowrie.client.version',
        'cowrie.client.kex',
        'cowrie.login.failed',
        'cowrie.session.closed',
        'cowrie.login.success',
        'cowrie.session.params',
        'cowrie.command.input',
        'cowrie.session.file_download',
        'cowrie.direct-tcpip.request',
        'cowrie.direct-tcpip.data',
        'cowrie.log.closed',
        'cowrie.command.failed',
        'cowrie.client.size',
        'cowrie.session.file_upload',
        'cowrie.session.file_download.failed'
    ]

    combined = list(zip(categories, probs))
    combined_sorted = sorted(combined, key = lambda x: x[1], reverse = True)
    top_5 = combined_sorted[:5]
    top_5_cat, top_5_prob = zip(*top_5)
    top_5_cat = list(top_5_cat)
    top_5_prob = list(top_5_prob)

    highest = max(probs)
    if highest > 0.8:
        conf = "Very High"
    elif highest > 0.6:
        conf = "High"
    elif highest > 0.4:
        conf = "Medium"
    elif highest > 0.2:
        conf = "Low"
    else:
        conf = "Very Low"

    data = {
        "categories": categories,
        "probabilities": probs,
        "top_5_cat": top_5_cat,
        "top_5_prob": top_5_prob, 
        "time": time,
        "attack_type": entry["attack_type"],
        "input_log": entry["input_log"],
        "attack_percentage": attack_percentage, 
        "conf": conf
    }

    return render(request, 'ask_me/detail.html', data)
