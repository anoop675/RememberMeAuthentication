<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Authentication</title>
</head>
<body>
	<style>
		body{
			background-color:grey;
		}
		.login-view{
			text-align:center;
			background-color:lightgrey;
			border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 80%;
            max-width: 500px;
            font-family:sans-serif, monospace;
		}
		.message{
			text-align:centre;
		}
		
		button{
			height: 30px;
			width: 300px;
			background-color: rgb(45, 45, 45);
			color: white;
			font-weight: bold;
			border-radius: 20px;
			border-style: solid;
		}
		
		input{
			width: 280px;
			margin-left: 105px;
		}
		.logout-message{
			color:blue;
			text-align:center;
			font-family:sans-serif, monospace;
		}
		.login-error-message{
			color:red;
			font-weight:bold;
			text-align:center;
			font-family:sans-serif, monospace;
		}
		
	</style>
	
	<div class="login-view">
	
		<form action="/doLogin" method="post">
	    	<h1>Login</h1>
	    	
	    	<p class="logout-message">${logoutSuccessMessage}</p>
	    	<p class="login-error-message">${loginFailureMessage}</p>
	    	
	        <div style=margin-right:125px;>
	        	<label style=margin-right:100px;>Username<br> </label>
	        	<input type="text" name="username">
	        </div ><br>
	        <div style=margin-right:125px;>
	        	<label style=margin-right:100px;>Password<br> </label>
	        	<input type="password" name="password">
	        </div>
	        <br>
	        <br>
	        <div style=margin-left:-40px;>
	        	<input style=margin-left:-120px type="checkbox" name="remember-me" value="true"/>
	        	<label style=margin-left:-135px;>Remember me on this computer</label>
	        </div>
	        <br>
	        <div><button name="submit" type="submit" >Login</button></div>
	    </form>
	    <br>
	    <p class="message">Not Registered? <a href="/api/v1/auth/register">Register here</a></p>
	    <br>
	</div>

</body>
</html>