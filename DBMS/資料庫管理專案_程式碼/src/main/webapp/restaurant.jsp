<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<style>
		.div1 {
    		float: right;
   			margin-right: 100px;
		}
	</style>
	<head>
    	<meta charset="utf-8">
    	<title>RestaurantChooser</title>  
    	<link rel="stylesheet" type="text/css" href="css/restaurantChooser.css"/> 
    	<meta name="viewpoint" content="width=device-width,initial-scale=1.0">
	</head>
	<body>
		<div class="system_name">
    		<img src="images/logo.png" width="200" height="150"  >
		</div>
		 
		<div class="restaurant_page">
    		<div id="container7">
        		<div class="intro">
            		<div class="tab"></div>
            		<h3>餐廳介紹</h3>
            		<div class="tab"></div><br>
            		
            		<img src="images/myFavorite.png" width="30" height="30" style="position: relative;bottom:10px;">
            		<b style="position: relative;bottom:20px;">${Name}</b>
            		<div class="tab"></div>
            		
            		<img src="images/location.png" width="30" height="30" style="position: relative;bottom:8px;">
            		<b style="position: relative;bottom:18px;">地址：${Address}</b>
            		<div class="tab"></div>
            		
            		<img src="images/phone.png" width="30" height="30" style="position: relative;bottom:6px;">
            		<b style="position: relative;bottom:16px;">電話：${Phone}</b>
            		<div class="tab"></div>
            		
            		<img src="images/clock.png" width="30" height="30" style="position: relative;bottom:4px;">
            		<b style="position: relative;bottom:14px;">營業時間：${BusHR}</b>
            		<div class="tab"></div>
            		
            		<img src="images/close.png" width="30" height="30" style="position: relative;bottom:2px;">
            		<b style="position: relative;bottom:12px;">公休日：${Closed}</b>
            		<div class="tab"></div>
            		
            		<img src="images/vegan.png" width="30" height="28" style="position: relative;bottom:1px;">
            		<b style="position: relative;bottom:9px;">是否提供素食餐點：${Vegan}</b>
            		<div class="tab"></div>
            		
            		<img src="images/star.png" width="30" height="30" style="position: relative;bottom:-7px;">
              		<b><a href = "javascript:void(0)" onclick = "toComment()" style="position: relative;bottom:0px;">點我到評價頁面</a></b>
            		<br>
            		
            		<img src="images/menu.png" width="30" height="30" style="position: relative;bottom:-7px;">
            		<b><a href = "javascript:void(0)" onclick = "toMenu()" style="position: relative;bottom:-2px;">點我到菜單頁面</a></b>
            		<br>
            		
            		<form action='/Final_Project_G4/MyFavoritePage?${user}&${RestID}' method='post'>
            			<input type="submit" value="加入我的最愛" name = "${user}" class="submit">
            			<input type="button" value="點我查看餐廳評價" class="submit" onclick="toRestaurantComment()">
            		</form>
            		<input type="button" value="返回上一頁" class="submit" onclick="toLastPage()">
            		<input type="button" value="返回至功能選單" class="submit" onclick="toFunctionList()">
        		</div>
    		</div>
		</div>
	</body>
	<script>
		function toComment(){
			if(${user} == "1"){
				alert("體驗時無法使用此功能");
			} else {
				document.location.assign("/Final_Project_G4/CommentPage?${user}&${RestID}");
			}
		}
		
		function toMenu(){
			document.location.assign("/Final_Project_G4/RestaurantMenuPage?${user}&${RestID}");
		}
		
		function toSearch(){
			document.location.assign("/Final_Project_G4/SearchPage?${user}");
		}
		
		function toLastPage(){
			document.location.assign("${last}");
		}
		
		function toRestaurantComment(){
			document.location.assign("/Final_Project_G4/RestaurantCommentPage?${user}&${RestID}");
		}
	
  		function toFunctionList(){
  			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
  	</script>
</html>
