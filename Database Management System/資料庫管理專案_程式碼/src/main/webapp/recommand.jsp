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
  			<img src="images/logo.png" width="200" height="150"  >
		</div>

		<div class="login_page">
  			<div id="container7">
    			<div class="favorite">
      				<h3>推薦結果</h3>
      				
      				<br>
      				<img src="images/firstplace.png" width="30" height="30" style="position: relative;top:12px;">
      				<a href="javascript:void(0)" onclick = 'toRest1()'>${Rest1}</a>
      				<br><br>
      				
      				<img src="images/secondplace.png" width="30" height="30" style="position: relative;top:12px;">
      				<a href="javascript:void(0)" onclick = 'toRest2()'>${Rest2}</a>
      				<br><br>
      				
      				<img src="images/thirdplace.png" width="30" height="30" style="position: relative;top:12px;">
      				<a href="javascript:void(0)" onclick = 'toRest3()'>${Rest3}</a>
      				<br><br>
      				
      				<input type="button" value="返回至主選單" class="submit"  style="position: relative;top:30px;" onclick="toFunctionList()">
    			</div>
  			</div>
		</div>
	</body> 
	<script>
  		function toFunctionList(){
  			document.location.assign("/Final_Project_G4/FunctionListPage?${user}");
		}
  		
  		function toRest1(){
  			if(${RestID1} != "none"){
  				document.location.assign("/Final_Project_G4/RestaurantPage?${user}&RestID=${RestID1}");
  			}
  		}
  		
  		function toRest2(){
  			if(${RestID2} != "none"){
  				document.location.assign("/Final_Project_G4/RestaurantPage?${user}&RestID=${RestID2}");
  			}
  		}
  		
  		function toRest3(){
  			if(${RestID3} != "none"){
  				document.location.assign("/Final_Project_G4/RestaurantPage?${user}&RestID=${RestID3}");
  			}
  		}
	</script>
</html>