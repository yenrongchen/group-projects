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
		<div class="personal_info_page">
			<div id="container1">
				<div class="personal info">
					<h3>更改密碼頁面</h3>
					<form action="/Final_Project_G4/ChangePasswordPage?${user}" method="post">
						<input type="text" id="username2" name="username2"placeholder="需混合4~8個英文字母和數字" required>
						<div class="tab"></div>
						<input type="text" id="username2" name="reusername2" placeholder="再確認一次" required>
						<div class="tab"></div>
						<input type="submit" value="更改完成" class="submit">
					</form> 
				</div>
			</div>
		</div>
	</body>
</html>