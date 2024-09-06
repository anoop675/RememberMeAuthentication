package com.anoopsen.RememberMe_SpringSecurityDemo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder {

	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder(16);
		DelegatingPasswordEncoder delPasswordEncoder = 
				(DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
		BCryptPasswordEncoder bcryptPasswordEncoder =new BCryptPasswordEncoder();
		delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
		
    return delPasswordEncoder; 
	}
}
