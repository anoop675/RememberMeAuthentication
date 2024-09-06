package com.anoopsen.RememberMe_SpringSecurityDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anoopsen.RememberMe_SpringSecurityDemo.model.RegisterRequest;
import com.anoopsen.RememberMe_SpringSecurityDemo.model.Role;
import com.anoopsen.RememberMe_SpringSecurityDemo.model.User;
import com.anoopsen.RememberMe_SpringSecurityDemo.repository.UserRepository;
import com.anoopsen.RememberMe_SpringSecurityDemo.security.MyPasswordEncoder;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	MyPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with username '"+username+"' is not found");
		}
		
		return user;
	}
	
	public void addUser(RegisterRequest request) {
		
		var user = User.builder()
					   .username(request.getUsername())
					   .password(passwordEncoder.passwordEncoder().encode(request.getPassword()))
					   .role(Role.USER)
					   .build()
					   ;
		
		userRepo.save(user);

	}
}
