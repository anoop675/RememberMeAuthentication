package com.anoopsen.RememberMe_SpringSecurityDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RememberMeSpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RememberMeSpringSecurityDemoApplication.class, args);
	}
	
	/*
	 	Remember-me is an authentication concept to auto-authenticate users even after their
	 	session expires. The JSESSIONID cookie will get expired, but the remember-me cookie
	 	only expires once its expiry data has reached. So the user can auto-login until the 
	 	remember-me cookie expires. 
	 	
	 	NOTE: remember-me aut-logs in users, if session expiry is caused, if the browser is closed
	 		  not if the session expiry is caused, if the user manually logs-out
	 		  
	 		  because, in spring security, if user ends the session by logging out, then
	 		  all the cookies in the browser will be erased.
	 */

}
