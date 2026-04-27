package com.HireFlow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	@GetMapping("/api/admin")
	public String test() {
		return "Admin page";
	}
}
