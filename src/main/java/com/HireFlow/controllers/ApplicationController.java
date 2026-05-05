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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HireFlow.dto.ApplicationResponse;
import com.HireFlow.dto.CompanyApplicationResponse;
import com.HireFlow.service.ApplicationService;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Seeker job pe apply kare
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PostMapping("/seeker/jobs/{jobId}/apply")
    public ResponseEntity<String> applyForJob(@PathVariable Long jobId) {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        String email = auth.getName();
        return ResponseEntity.ok(
            applicationService.applyForJob(jobId, email));
    }

    // Seeker apni applications dekhe
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @GetMapping("/seeker/applications")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications() {
        Authentication auth = SecurityContextHolder
            .getContext().getAuthentication();
        String email = auth.getName();
        return ResponseEntity.ok(
            applicationService.getMyApplications(email));
    }

    // Company dekhe kisne apply kiya
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    @GetMapping("/company/jobs/{jobId}/applications")
    public ResponseEntity<List<CompanyApplicationResponse>> getApplicationsForJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(
            applicationService.getApplicationsForJob(jobId));
    }

    // Company status update kare
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    @PutMapping("/company/applications/{appId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long appId,
            @RequestParam String status) {
        return ResponseEntity.ok(
            applicationService.updateStatus(appId, status));
    }
}