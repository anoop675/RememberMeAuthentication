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
        .timer{
        	font-size: 50px;
            margin-right: 100px; /* Adjust the margin as needed */
        }
        
    </style>
</head>
<body>
	<script>
		// Function to format seconds into HH:MM:SS format
		function formatTime(seconds) {
		    let hours = Math.floor(seconds / 3600);
		    let minutes = Math.floor((seconds % 3600) / 60);
		    let remainingSeconds = seconds % 60;
		
		    hours = (hours < 10) ? '0' + hours : hours;
		    minutes = (minutes < 10) ? '0' + minutes : minutes;
		    remainingSeconds = (remainingSeconds < 10) ? '0' + remainingSeconds : remainingSeconds;
		
		    return hours + ':' + minutes + ':' + remainingSeconds;
		}
		
		// Function to update the timer
		function updateTimer() {
		    // Initialize the timer duration in seconds
		    let seconds = 0;
		
		    // Update the timer every second
		    setInterval(function() {
		        document.getElementById('timer').innerText = formatTime(seconds);
		        seconds++;
		    }, 1000);
		}
		
		// Call the updateTimer function to start the timer
		updateTimer();
		
	</script>
	
	<div id="timer">00:00:00</div>
	
    <div class="dashboard">
        <h1>Dashboard</h1>
        <h2>Welcome back, ${username}</h2>
        <br>
        
        <p>Click here to view <a href="/api/v1/home/getUsers">Logged-in users</a>
        <p>Click here to <a href="/do-logout">Logout</a></p>

    </div>
</body>
</html>