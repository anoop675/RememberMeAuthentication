package com.anoopsen.RememberMe_SpringSecurityDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anoopsen.RememberMe_SpringSecurityDemo.service.MyUserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/v1/home")
public class HomeController {
	
	@Autowired
	MyUserService userService;
	
	@GetMapping(value = "/dashboard")
	public ModelAndView renderDashboard() {
		ModelAndView mv =  new ModelAndView("/index.jsp");
		
		mv.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
		return mv;
		
	}
	
	@GetMapping(value = "/getUsers")
	public ModelAndView renderGetUsersPage() {
		ModelAndView mv = new ModelAndView("/getUsers.jsp");
		
		List<UserDetails> loggedInUsers = userService.getAllUsers();
		mv.addObject("users",loggedInUsers);
		
		return mv;
	}
	
	@GetMapping(value = "/getUser/{username}")
	public ModelAndView renderGetUsersPage(@RequestParam String username) {
		ModelAndView mv = new ModelAndView("/getUsers.jsp");
		
		UserDetails loggedInUser = userService.getUser(username);
		mv.addObject("users",loggedInUser);
		
		return mv;
	}
}
