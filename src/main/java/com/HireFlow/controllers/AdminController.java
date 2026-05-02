package com.HireFlow.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Admin Dashboard Here";
	}
	
}
