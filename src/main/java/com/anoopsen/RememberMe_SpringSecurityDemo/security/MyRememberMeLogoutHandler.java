package com.anoopsen.RememberMe_SpringSecurityDemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

/*
public class MyRememberMeLogoutHandler implements LogoutHandler {

    private String rememberMeCookieName;

    public MyRememberMeLogoutHandler(String rememberMeCookieName) {
        this.rememberMeCookieName = rememberMeCookieName;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Check if the "remember-me" parameter is set, indicating that the user requested Remember-Me
        String rememberMeParameter = request.getParameter("remember-me");

        if (StringUtils.hasLength(rememberMeParameter)) {
            // The user requested Remember-Me, so don't clear the Remember-Me cookie
        	System.out.println("Yes"+rememberMeParameter);
        	
        	
            return;
        }

        System.out.println("No"+rememberMeParameter);
        // Remove the Remember-Me cookie
        Cookie rememberMeCookie = new Cookie(rememberMeCookieName, null);
        rememberMeCookie.setMaxAge(0);
        rememberMeCookie.setPath("/");
        response.addCookie(rememberMeCookie);
    }
}*/

public class MyRememberMeLogoutHandler implements LogoutHandler {

    private String rememberMeCookieName;

    public MyRememberMeLogoutHandler(String rememberMeCookieName) {
        this.rememberMeCookieName = rememberMeCookieName;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Check if the "remember-me" parameter is set, indicating that the user requested Remember-Me
        String rememberMeParameter = request.getParameter("remember-me");

        if (StringUtils.hasLength(rememberMeParameter)) {
            // The user requested Remember-Me, so don't clear the Remember-Me cookie
        	System.out.println("Yes"+rememberMeParameter);
            return;
        }

        // Remove the Remember-Me cookie
        System.out.println("No"+rememberMeParameter);
        Cookie rememberMeCookie = new Cookie(rememberMeCookieName, null);
        rememberMeCookie.setMaxAge(0);
        rememberMeCookie.setPath("/");
        response.addCookie(rememberMeCookie);
    }
}
    




