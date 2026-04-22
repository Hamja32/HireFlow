package com.HireFlow.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HireFlow.dto.LoginRequest;
import com.HireFlow.dto.RegisterRequest;
import com.HireFlow.entity.Role;
import com.HireFlow.entity.User;
import com.HireFlow.enums.RoleName;
import com.HireFlow.repository.RoleRepo;
import com.HireFlow.repository.UserRepo;
import com.HireFlow.utils.JwtUtils;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepo userRepo;


@Autowired
private RoleRepo roleRepo;

public String register(RegisterRequest request) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    // Password encode karke save karo
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // Role assign karo
    Role role = roleRepo.findByName(RoleName.ROLE_SEEKER)
        .orElseThrow(() -> new RuntimeException("Role not found"));
    
    user.setRoles(Set.of(role));
    userRepo.save(user);
    
    return "User registered successfully";
}


	public AuthResponse login(LoginRequest loginRequest) {
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
		);
		User user = (User) auth.getPrincipal();
		String token = jwtUtils.generateToken(loginRequest.getEmail());
		return new AuthResponse(token);
	}
}
