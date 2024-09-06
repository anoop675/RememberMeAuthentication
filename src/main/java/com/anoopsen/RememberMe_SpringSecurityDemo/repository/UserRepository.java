package com.anoopsen.RememberMe_SpringSecurityDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.anoopsen.RememberMe_SpringSecurityDemo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public UserDetails findByUsername(String username);
	

}
