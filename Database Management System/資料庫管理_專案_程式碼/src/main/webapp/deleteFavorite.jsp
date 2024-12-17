<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head> 
		<meta charset="utf-8">
		<title>RestaurantChooser</title>  
		<link rel="stylesheet" type="text/css" href="css/restaurantChooser.css"/> 
		<meta name="viewpoint" content="width=device-width,initial-scale=1.0">
	</head>
	<body>
		<div class="login_page">
			<div id="container7">
				<div class="favorite"> 
					<h3>刪除我的最愛</h3>
					<form action = '/Final_Project_G4/DeleteFavoritePage?${user}' method = 'post'>
						<div style="position: relative;top:30px;">
							<input type = "checkbox" name = "delete1" value = "del1" style="width: 18px; height: 18px; position: relative; top:3px;">
							<span>${Rest1}</span>
							<img src="images/cross.png" width="20" height="20" style="position: relative;top:5px;left:10px">
						</div>
						<div class="tab" ></div>
						
						<div style="position: relative;top:90px;" >
							<input type = "checkbox" name = "delete2" value = "del2" style="width: 18px; height: 18px; position: relative; top:3px;">
							<span>${Rest2}</span>
							<img src="images/cross.png" width="20" height="20" style="position: relative;top:5px;left:10px">
							</div>
						<div class="tab" ></div>
						
						<div style="position: relative;top:150px;">
							<input type = "checkbox" name = "delete3" value = "del3" style="width: 18px; height: 18px; position: relative; top:3px;">
							<span>${Rest3}</span>
							<img src="images/cross.png" width="20" height="20" style="position: relative;top:5px;left:10px">
						</div>
						<div class="tab" ></div>
						
						<input type="submit" value="確認刪除" class="submit" style="position: relative; top: 180px">
        			</form>
					<input type="button" value="回到我的最愛" class="submit" style="position: relative; top: 180px" onclick="toMyFavorite()">
					<h5 style= "position:relative; top:170px;"><a href="javascript:void(0)" onclick="toFunctionList()">返回主選單</a></h5>
				</div>
			</div>
		</div>
	</body> 
	<script>
  		function toFunctionList(){
  			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
  		
  		function toMyFavorite(){
  			document.location.assign("/Final_Project_G4/MyFavoritePage?${user}");
		}
	</script>
</html>