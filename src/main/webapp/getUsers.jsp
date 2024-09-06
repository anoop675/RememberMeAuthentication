<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        /* CSS for the dashboard container */
        .dashboard {
            background-color: #fff; /* White background */
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            width: 80%;
            max-width: 800px;
            max-length: 1300px;
            font-family:sans-serif, monospace;
        }
        .logged-in-users{
        	text-align:centre;
        	font-family:monaco, monospace;
        }
        
    </style>
</head>
<body>
    <div class="get-user-page">
    
    	<div class="logged-in-users">
    		${users}
    	</div>
        
        <p>Click here to <a href="/api/v1/home/dashboard">Go back</a></p>

    </div>
</body>
</html>