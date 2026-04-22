package com.HireFlow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HireFlow.dto.LoginRequest;
import com.HireFlow.dto.RegisterRequest;
import com.HireFlow.service.AuthResponse;
import com.HireFlow.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	public AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginReq){
		return ResponseEntity.ok(authService.login(loginReq));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(authService.register(request));
	}
}
