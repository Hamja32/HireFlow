package com.HireFlow.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@PreAuthorize("hasAuthority('ROLE_COMPANY')")
	@GetMapping("/dashboard")
	public String getDashboard() {
		return "Company Dashboard Here";
	}
}
