package com.HireFlow.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.HireFlow.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {


    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @GetMapping("/seeker/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
    	return userService.getProfile(auth);

    }
    
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    @GetMapping("/company/profile")
    public ResponseEntity<?> getCompanyProfile(Authentication auth) {
      return userService.getCompProfile(auth);
    }
}