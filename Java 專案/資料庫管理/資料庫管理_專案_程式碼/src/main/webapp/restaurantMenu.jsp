<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="BIG5">
		<title>RestaurantChooser</title>
		<link rel="stylesheet" type="text/css" href="css/restaurantChooser.css" />
		<meta name="viewpoint" content="width=device-width,initial-scale=1.0">
	</head>
	<body>
		<div class="restaurant_page">
			<br>
			<div style="text-align: center;">
				<img src="images/fork.png" width="30" height="30" style="margin-right: 10px;">
				<h3 style="display: inline-block; margin: 0;">${name}的菜單</h3>
				<img src="images/diet.png" width="30" height="30" style="margin-left: 10px;">
			</div>

			<div id="container8">
				<div class="intro">
					<label style="position: relative; left: -320px; top: 0px; font-weight: bold;">菜單圖片：</label>
					<h4 style="position: absolute; left: 20px; bottom: 5px; top: 20px; text-align: left">${source}</h4>
					<div class="tab"></div>
					<label style="position: absolute; top: 10px; right: 300px; font-weight: bold;">菜單細項：</label>
					<div style="height: 330px; width: 330px; overflow: auto; position: absolute; right: 53px; top: 35px; text-align: left;">
						<table>${menu}</table>
					</div>
					<input type="button" value="返回上一頁" class="submit" style="position: absolute; bottom: 2px; left: 150px;" onclick="toLastPage()"> 
					<input type="button" value="返回至功能選單" class="submit" style="position: absolute; bottom: 2px; right: 150px;" onclick="toFunctionList()">
				</div>
			</div>
		</div>
	</body>
	<script>
		function toLastPage() {
			document.location.assign("/Final_Project_G4/RestaurantPage?${user}&${RestID}");
		}

		function toFunctionList() {
			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
	</script>
</html>
