package com.HireFlow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.HireFlow.repository.UserRepo;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) 
	    throws UsernameNotFoundException {
	    return repo.findByEmail(email).orElseThrow(
	        () -> new UsernameNotFoundException("User not found: " + email)
	    );
	}


}
