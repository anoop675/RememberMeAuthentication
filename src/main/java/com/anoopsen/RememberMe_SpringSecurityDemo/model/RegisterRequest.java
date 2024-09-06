package com.anoopsen.RememberMe_SpringSecurityDemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
//import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
//@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	/*
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	*/
}
