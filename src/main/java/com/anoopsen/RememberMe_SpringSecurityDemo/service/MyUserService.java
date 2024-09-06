package com.anoopsen.RememberMe_SpringSecurityDemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anoopsen.RememberMe_SpringSecurityDemo.repository.UserRepository;

@Service
public class MyUserService {

	@Autowired
	UserRepository userRepo;
	
	public List<UserDetails> getAllUsers(){
		
		return userRepo.findAll()
					   .stream()
					   .map(user -> (UserDetails)user)
					   .collect(Collectors.toList());
	}
	
	public UserDetails getUser(String username) {
		
		return userRepo.findByUsername(username);
	}
}
