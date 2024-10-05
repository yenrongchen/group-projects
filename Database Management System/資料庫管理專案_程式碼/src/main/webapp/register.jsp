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
			<h2>餐廳選擇器</h2>
		</div>
		
		<div class="signup_page">
			<div id="container2">
				<div class="signup">            
					<h3>註冊 Sign Up</h3><br>
         
					<form action='/Final_Project_G4/RegisterPage' method='post'>
						<input type="text" id="account" name="account" style="position: relative; left: 93px" placeholder="使用者帳號" required>
						<label style="position: relative; left: 100px; font-size:14px; color:#808080">需混合4~8個英文字母和數字</label>
						<div class="tab" ></div>
						
						<input type="text" id="username2" name="email" placeholder="您的信箱" required>
						<div class="tab"></div>
						
						<input type="text" id="password2" name="password" placeholder="您的密碼" required>
						<div class="tab"></div>
						
						<input type="text" id="comfirm_password" name="confirm_password" placeholder="確認密碼" required>
						<div class="tab"></div>  
						          
						<input type="submit" value="註冊" class="submit">
					</form>  
					<h5><a href ="javascript:void(0)" onclick="toLogin()">登入帳號</a></h5>
          			<!-- 當使用者點擊按鈕呼叫函式-->
				</div><!-- signup end-->
			</div><!-- container2 end-->
		</div><!-- signup_page end--> 
	</body>
	<script> 
  		function toLogin(){
  			document.getElementById("account").value = "";
  			document.getElementById("username2").value = "";
  			document.getElementById("password2").value = "";
  			document.getElementById("comfirm_password").value = "";
        	window.location.replace("/Final_Project_G4/LoginPage");
  		}  	
	</script>
</html>
