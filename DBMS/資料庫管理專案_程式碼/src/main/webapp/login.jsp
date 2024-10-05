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
		<div class="system_name">
			<img src="images/logo.png" width="200" height="150"  >
		</div>
		
		<div class="system_name">
			<h2>餐廳選擇器</h2>
		</div>
		
		<div class="login_page">
			<div id="container1">
				<div class="login">       
					<h3>登入 Login</h3><br>
        
					<form action='/Final_Project_G4/LoginPage' method='post'><!-- 後端-->
						<input type="text" id="account" name="account" placeholder="帳號" required>
						<div class="tab"></div>
						
						<input type="password" id="password" name="password" placeholder="密碼" required>
						<div class="tab"></div>
						
						<input type="submit" value="登入" class="submit"><!--點進就登入，要寫登入function-->
					</form>  

					<input type = "button" value="體驗功能" class="submit" onclick = "guestLogin()">
					<h5><a href = "javascript:void(0)" onclick = "toRegister()">註冊帳號</a></h5>
          
				</div><!-- login end-->
			</div><!-- container1 end-->
		</div><!-- login_page end-->
	</body> 
	<script>
  		function toRegister(){
  			document.getElementById("account").value = "";
  			document.getElementById("password").value = "";
      		window.location.replace("/Final_Project_G4/RegisterPage");
  		}
  		
  		function guestLogin(){
  			document.location.assign("/Final_Project_G4/FunctionListPage?id=1");
  		}
	</script>
</html>

  
