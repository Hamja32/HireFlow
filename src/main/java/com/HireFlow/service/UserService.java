package com.HireFlow.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.HireFlow.entity.User;
import com.HireFlow.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;


	
	public ResponseEntity<?> getProfile(Authentication auth) {
		  User user = userRepo.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));
	
	        Map<String, Object> profile = new HashMap<>();
	        profile.put("name", user.getName());
	        profile.put("email", user.getEmail());
	        profile.put("role", user.getRoles());        
	        return ResponseEntity.ok(profile);
	}

	
	
}
