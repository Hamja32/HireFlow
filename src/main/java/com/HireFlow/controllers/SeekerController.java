package com.HireFlow.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seeker")
public class SeekerController {
	@PreAuthorize("hasAuthority('ROLE_SEEKER')")
	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Seeker Dashboard Here";
	}
}
