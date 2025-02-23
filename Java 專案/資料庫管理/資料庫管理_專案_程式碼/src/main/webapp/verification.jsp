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
		
		<div id="container1">
			<div class="signup">
				<h3>驗證你的email</h3>
			
				<form action='/Final_Project_G4/VerificationPage' method='post'>
					<input type="text" id="account" name="verification" style="position: relative; left: 77px" placeholder="驗證碼" required>
					<label style="position: relative; left: 100px; font-size:14px; color:#808080">已透過email發送驗證碼</label>
					<div class="tab" ></div>
					<input type="submit" value="送出" class="submit">
				</form>
			</div>
		</div>
	</body>
</html>