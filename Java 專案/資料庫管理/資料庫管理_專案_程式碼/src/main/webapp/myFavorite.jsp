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
					<h3>我的最愛</h3>
						<div style="position: relative;top:30px;">
							<img src="images/firstplace.png" width="30" height="30" style="position: relative;top:12px;">
							<span>${Rest1}</span>
							<img src="images/myFavorite.png" width="30" height="30" style="position: relative;top:12px;">
						</div>
						<div class="tab" ></div>
						
						<div style="position: relative;top:90px;" >
							<img src="images/secondplace.png" width="30" height="30" style="position: relative;top:12px;">
							<span>${Rest2}</span>
							<img src="images/myFavorite.png" width="30" height="30"style="position: relative;top:12px;" >
							</div>
						<div class="tab" ></div>
						
						<div style="position: relative;top:150px;">
							<img src="images/thirdplace.png" width="30" height="30" style="position: relative;top:12px;">
							<span>${Rest3}</span>
							<img src="images/myFavorite.png" width="30" height="30" style="position: relative;top:12px;" >
						</div>
						<div class="tab" ></div>
						
						<input type="button" value="刪除我的最愛" class="submit" style="position: relative; top: 200px"onclick="toDeleteFavorite()">
        
						<h5 style= "position:relative; top:188px;"><a href="javascript:void(0)" onclick="toFunctionList()">返回主選單</a></h5>
                
				</div>
			</div>
		</div>
	</body>
	<script>
  		function toFunctionList(){
  			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
  		
  		function toDeleteFavorite(){
  			document.location.assign("/Final_Project_G4/DeleteFavoritePage?${user}");
		}
	</script>
</html>