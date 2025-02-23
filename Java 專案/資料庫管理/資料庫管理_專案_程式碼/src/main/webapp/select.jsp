<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<style>
	label {
		padding: 0;
		margin-right: 3px;
		cursor: pointer;
		position: relative;
		bottom: 12px;
	}

	input[type=checkbox] {
		display: none;
		position: relative;
		bottom: 12px;
	}

	input[type=checkbox]+span {
		display: inline-block;
		background-color: #f8f2f2;
		padding: 3px 6px;
		border: 1px solid rgb(255, 251, 251);
		color: #000000;
		border-radius: 10px;
		user-select: none;
		position: relative;
		bottom: 12px; /* 防止文字被滑鼠選取反白 */
	}

	input[type=checkbox]:checked+span {
		color: rgb(220, 160, 62);
		background-color: #dcd5d5;
		position: relative;
		bottom: 12px;
	}
	</style>
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
		<div class="option_page">
			<div id="container4">
				<div class="options">
					<h3>選擇介面</h3>
					<form action='/Final_Project_G4/SelectPage?${user}' method='post'>
						<br> <br>
						<b><label style="position: relative; bottom: 25px;">預算:</label></b> 
						<label><input type="checkbox" id="budget1" name="budget1" value="100元以下" onclick="checkBudget()">
							<span>100元以下</span>
						</label>
						<label><input type="checkbox" id="budget2" name="budget2" value="100-150元" onclick="checkBudget()">
							<span>100-150元</span>
						</label>
						<label><input type="checkbox" id="budget3" name="budget3" value="150-200元" onclick="checkBudget()">
							<span>150-200元</span>
						</label>
						<label><input type="checkbox" id="budget4" name="budget4" value="200-250元" onclick="checkBudget()">
							<span>200-250元</span>
						</label>
						<label><input type="checkbox" id="budget5" name="budget5" value="250元以上" onclick="checkBudget()">
							<span>250元以上</span>
						</label>
						<label><input type="checkbox" id="checkAllB" onclick="selectAllBudget(this)">
							<span>不限</span>
						</label>

						<div class="tab"></div>
						<br>
						<b><label style="position: relative; bottom: 25px;">用餐時間:</label>
						</b> 
						<label><input type="checkbox" id="time1" name="time1" value="30分以下" onclick="checkTime()">
							<span>30分以下</span>
						</label> 
						<label><input type="checkbox" id="time2" name="time2" value="30分-1小時" onclick="checkTime()">
							<span>30分-1小時</span>
						</label> 
						<label>
						<input type="checkbox" id="time3" name="time3" value="1小時以上" onclick="checkTime()">
							<span>1小時以上</span>
						</label> 
						<label>
						<input type="checkbox" id="checkAllt" onclick="selectAllTime(this)">
							<span>不限</span>
						</label>

						<div class="tab"></div>
						<br>
							<b><label style="position: relative; bottom: 25px;">口味:</label></b> 
						<label>
						<input type="checkbox" id="type1" name="type1" value="中式" onclick="checkType()">
							<span>中式餐廳</span>
						</label>
						<label>
						<input type="checkbox" id="type2" name="type2" value="韓式日式美式義式" onclick="checkType()">
							<span>韓式、日式、美式、義式餐廳</span>
						</label> 
						<label>
						<input type="checkbox" id="type3" name="type3" value="輕食速食" onclick="checkType()">
							<span>輕食、速食</span>
						</label>
						<label>
						<input type="checkbox" id="checkAllT" onclick="selectAllType(this)">
							<span>不限</span>
						</label>

						<div class="tab"></div>
						<br>
						<b><label style="position: relative; bottom: 25px;">距離何處較近:</label></b>
						<label><input type="checkbox" id="dist1" name="dist1" value="麥側" onclick="checkDistance()">
							<span>麥側</span>
						</label>
						<label>
						<input type="checkbox" id="dist2" name="dist2" value="正門" onclick="checkDistance()">
							<span>正門</span>
						</label>
						<label>
						<input type="checkbox" id="dist3" name="dist3" value="東側門" onclick="checkDistance()">
							<span>東側門</span>
						</label> 
						<label>
						<input type="checkbox" id="checkAllD" onclick="selectAllDistance(this)">
							<span>不限</span>
						</label>
						<br>

						<input type="submit" value="選擇完成" class="submit" style="position: relative;">
					</form>
					<h5 style="position: relative;">
					<a href="javascript:void(0)" onclick="toFunctionList()">返回主選單</a>
					</h5>
				</div>
			</div>
		<!-- container3 end-->
		</div>
	</body>
	<script>
		function toRecommand() {
			document.location.assign("/Final_Project_G4/RecommandPage?${user}")
		}

		function toFunctionList() {
			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}

		function selectAllBudget(obj) {
			for (var i = 1; i <= 5; i++) {
				document.getElementById("budget" + i.toString()).checked = obj.checked;
			}
		}

		function checkBudget() {
			var checkedCount = 0;
			for (var i = 1; i <= 5; i++) {
				if (document.getElementById("budget" + i.toString()).checked) {
					checkedCount++;
				}
			}
			var checkAllBCheckbox = document.getElementById('checkAllB');
			if (checkedCount == 5) {
				checkAllBCheckbox.checked = true;
			}
			if (checkedCount == 4 && checkAllBCheckbox.checked) {
				checkAllBCheckbox.checked = false;
			}
		}

		function selectAllTime(obj) {
			for (var i = 1; i <= 3; i++) {
				document.getElementById("time" + i.toString()).checked = obj.checked;
			}
		}

		function checkTime() {
			var checkedCount = 0;
			for (var i = 1; i <= 3; i++) {
				if (document.getElementById("time" + i.toString()).checked) {
					checkedCount++;
				}
			}
			var checkAllBCheckbox = document.getElementById('checkAllt');
			if (checkedCount == 3) {
				checkAllBCheckbox.checked = true;
			}
			if (checkedCount == 2 && checkAllBCheckbox.checked) {
				checkAllBCheckbox.checked = false;
			}
		}

		function selectAllType(obj) {
			for (var i = 1; i <= 3; i++) {
				document.getElementById("type" + i.toString()).checked = obj.checked;
			}
		}

		function checkType() {
			var checkedCount = 0;
			for (var i = 1; i <= 3; i++) {
				if (document.getElementById("type" + i.toString()).checked) {
					checkedCount++;
				}
			}
			var checkAllBCheckbox = document.getElementById('checkAllT');
			if (checkedCount == 3) {
				checkAllBCheckbox.checked = true;
			}
			if (checkedCount == 2 && checkAllBCheckbox.checked) {
				checkAllBCheckbox.checked = false;
			}
		}

		function selectAllDistance(obj) {
			for (var i = 1; i <= 3; i++) {
				document.getElementById("dist" + i.toString()).checked = obj.checked;
			}
		}

		function checkDistance() {
			var checkedCount = 0;
			for (var i = 1; i <= 3; i++) {
				if (document.getElementById("dist" + i.toString()).checked) {
					checkedCount++;
				}
			}
			var checkAllBCheckbox = document.getElementById('checkAllD');
			if (checkedCount == 3) {
				checkAllBCheckbox.checked = true;
			}
			if (checkedCount == 2 && checkAllBCheckbox.checked) {
				checkAllBCheckbox.checked = false;
			}
		}
	</script>
</html>
