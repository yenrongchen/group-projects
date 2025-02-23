<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>RestaurantChooser</title>
		<link rel="stylesheet" type="text/css" href="css/restaurantChooser.css" />
		<meta name="viewpoint" content="width=device-width,initial-scale=1.0">
	</head>
	<body>
		<div class="system_name">
			<img src="images/logo.png" width="200" height="150">
		</div>

		<div class="search_page">
			<div id="container6">
				<div class="search">
					<h3>餐廳查詢</h3>
					<br>
					<form action='/Final_Project_G4/SearchPage?${user}' method='post'>
						<input type="text" name="name" placeholder="輸入關鍵字" list="rest">
						<datalist id="rest">
							<option value="梁社漢">
							<option value="傳香飯糰">
							<option value="MY麵屋">
							<option value="杈奔">
							<option value="左撇子">
							<option value="Mr. Light 輕食先生">
							<option value="敏忠小吃">
							<option value="滇味廚房">
							<option value="華越">
							<option value="喜記">
							<option value="小確幸">
							<option value="八方雲集">
							<option value="波波恰恰">
							<option value="飽飽食府">
							<option value="菁英便當">
							<option value="佳味自助餐">
							<option value="四川飯館">
							<option value="集英樓">
							<option value="美香味">
							<option value="素還真">
							<option value="蛋蛋的幸福">
							<option value="季旭小吃">
							<option value="同洋燒臘">
							<option value="廢墟">
							<option value="私房麵">
							<option value="悅來麵食">
							<option value="政大排骨王">
							<option value="丐幫滷味">
							<option value="品佳">
							<option value="湘湘牛排館">
							<option value="焿大王">
							<option value="珍妹麵店">
							<option value="食鼎鵝肉">
							<option value="指南山下">
							<option value="政大赤肉羹滷肉飯">
							<option value="政大烤場">
							<option value="老味道鍋物">
							<option value="高句麗">
							<option value="韓大佬">
							<option value="阿里郎">
							<option value="石鍋軒">
							<option value="金鮨日式料理">
							<option value="東京小城">
							<option value="我和一心">
							<option value="鬼匠拉麵">
							<option value="展弟拉麵">
							<option value="追夢輸送">
							<option value="吉野家">
							<option value="原丼力">
							<option value="浪速食鋪">
							<option value="Juicy Bun">
							<option value="提洛斯">
							<option value="Lazy Pasta">
							<option value="首思義">
							<option value="舒曼六號">
							<option value="小曼谷">
							<option value="小木屋">
							<option value="711 政大店">
							<option value="711 金恩店">
							<option value="全家 興政店">
							<option value="全家 政富店">
							<option value="萊爾富 痴心店(商萊)">
							<option value="萊爾富 指南店">
							<option value="麥當勞">
							<option value="摩斯漢堡">
							<option value="Come See披薩">
							<option value="大汗">
							<option value="政大關東煮">
							<option value="H.I.Feeling">
							<option value="江記水盆羊肉">
							<option value="MJ餛飩超人">
							<option value="呷麵飯館">
						</datalist>
					<input type="submit" value="搜尋">
					<!--這里應該用submit-->
					</form>
					<br> <input type="button" value="返回至功能選單" class="submit" onclick="toFunctionList()">
				</div>
			</div>
		</div>
	</body>
	<script>
		function toFunctionList() {
			window.location.replace("/Final_Project_G4/FunctionListPage?${user}");
		}
	</script>
</html>
