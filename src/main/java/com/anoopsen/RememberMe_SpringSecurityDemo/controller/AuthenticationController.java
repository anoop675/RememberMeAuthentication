package com.anoopsen.RememberMe_SpringSecurityDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.anoopsen.RememberMe_SpringSecurityDemo.exceptions.MyPasswordMismatchException;
import com.anoopsen.RememberMe_SpringSecurityDemo.model.RegisterRequest;
import com.anoopsen.RememberMe_SpringSecurityDemo.service.MyUserDetailsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@GetMapping(value = "/login")
	public ModelAndView renderLogin() {
		//login process is handled by Spring security under the URL "/doLogin"
		return new ModelAndView("/loginPage.jsp");
	}
	
	@GetMapping(value = "/register")
	public ModelAndView renderRegister() {
		return new ModelAndView("/registerPage.jsp");
	}
	
	
	@PostMapping(value = "/do-register")
	public ModelAndView registerUser(RegisterRequest request) throws Exception{
		
		ModelAndView mv = new ModelAndView("/registerPage.jsp");
		
		try {
			if(request.getUsername().equals("") || request.getPassword().equals("") || 
				request.getConfirmPassword().equals("")) {
				
				throw new BadCredentialsException("Bad Credentials");
			}
			else if(!request.getPassword().equals(request.getConfirmPassword())) {
				
				throw new MyPasswordMismatchException("Password Mismatch");
			}
			
			userDetailsService.addUser(request);   //if user credentials are duplicate, then throws PSQLException
			
			mv.addObject("registerSuccessMessage", "Registration Successful!");
		}
		catch(BadCredentialsException e) {
			mv.addObject("registerFailedMessage",
					"The credentials entered are empty. Try again");
		}
		catch(MyPasswordMismatchException e) {
			mv.addObject("registerFailedMessage", "Passwords do not match! Try again");
		}
		
		catch(Exception e) {
			mv.addObject("registerFailedMessage", "Entered credentials are already used! Try again");
		}
		
		return mv;
	}

	@GetMapping(value = "/logout")
	public ModelAndView logoutUser(/*HttpServletResponse response*/) {
		
		ModelAndView mv = new ModelAndView("/loginPage.jsp");
		
		// Manually expiring the session token in session cookie
		/* 
		Cookie sessionCookie = new Cookie("JSESSIONID", null);
	     sessionCookie.setMaxAge(0);
	     response.addCookie(sessionCookie);
		*/
		
		mv.addObject("logoutSuccessMessage", "You have been logged out");
		
		return mv;
	}
	
	@GetMapping(value = "/logout-expired")
	public ModelAndView logoutUserAfterRememberMeTokenExpires() {
		
		ModelAndView mv = new ModelAndView("/loginPage.jsp");
		
		// Manually expiring the session token in session cookie
		/* 
		Cookie sessionCookie = new Cookie("JSESSIONID", null);
	     sessionCookie.setMaxAge(0);
	     response.addCookie(sessionCookie);
		*/
		
		mv.addObject("logoutSuccessMessage", "Your session has expired, Please log in again");
		
		return mv;
	}
	
	@GetMapping(value = "/login/error")
	public ModelAndView loginError() {
		ModelAndView mv = new ModelAndView("/loginPage.jsp");
		
		mv.addObject("loginFailureMessage", "Login Failed!");
		
		return mv;
	}
}
