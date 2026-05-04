package com.HireFlow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HireFlow.dto.JobRequest;
import com.HireFlow.dto.JobResponse;
import com.HireFlow.entity.Job;
import com.HireFlow.service.JobService;

@RestController
@RequestMapping("/api")
public class JobController {

	
//	POST /api/company/jobs     → job post karo (COMPANY only)
//	GET  /api/seeker/jobs      → sari jobs dekho
//	GET  /api/seeker/jobs/{id} → ek job dekho
	
	
	@Autowired
	private JobService jobService;
	
	@PreAuthorize("hasAuthority('ROLE_COMPANY')")
	@PostMapping("/company/jobs")
	public ResponseEntity<String> jobPost(@RequestBody JobRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		System.out.println(email);
		return ResponseEntity.ok(jobService.postJob(req, email));
	}
	
	@PreAuthorize("hasAuthority('ROLE_SEEKER')")
	@GetMapping("/seeker/jobs")
	public ResponseEntity<List<JobResponse>> getAllJobs() {
	    return ResponseEntity.ok(jobService.getAllJobs());
	}

	@PreAuthorize("hasAuthority('ROLE_SEEKER')")
	@GetMapping("/seeker/jobs/{id}")
	public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
	    return ResponseEntity.ok(jobService.getJobById(id));
	}
}

