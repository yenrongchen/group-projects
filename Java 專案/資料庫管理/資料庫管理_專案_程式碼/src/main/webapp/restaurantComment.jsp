<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Restaurant Chooser</title>
		<link rel="stylesheet" type="text/css" href="css/restaurantChooser.css" />
		<meta name="viewpoint" content="width=device-width,initial-scale=1.0">
	</head>
	<body>
		<div class="system_name">
			<img src="images/logo.png" width="200" height="150">
		</div>
		<div class="commentView">
			<div id="container7">
				<div class="View">
					<h3>${Rest}的評論</h3>
					<br>
					<div style="height: 300px; width: 250px; overflow: auto; position: absolute; top: 60px; right: 225px; text-align: left;">
						<table>${comment}</table>
					</div>
					<input type="button" value="返回上一頁" class="submit" style="position: absolute; bottom: 70px; right: 280px;" onclick="toRestaurant()">
					<h5 style="position: absolute; bottom: 20px; right: 320px;">
						<a href="javascript:void(0)" onclick="toFunctionList()">返回至功能選單</a>
					</h5>
				</div>
			</div>
		</div>
	</body>
	<script>
		function toRestaurant() {
			document.location.assign("/Final_Project_G4/RestaurantPage?${user}&${RestID}");
		}
		
		function toFunctionList() {
			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
	</script>
</html>