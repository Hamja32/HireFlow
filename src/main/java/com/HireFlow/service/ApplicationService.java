package com.HireFlow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HireFlow.dto.ApplicationResponse;
import com.HireFlow.dto.CompanyApplicationResponse;
import com.HireFlow.entity.Application;
import com.HireFlow.entity.Job;
import com.HireFlow.entity.User;
import com.HireFlow.enums.ApplicationStatus;
import com.HireFlow.repository.ApplicationRepository;
import com.HireFlow.repository.JobRepository;
import com.HireFlow.repository.UserRepo;

@Service
public class ApplicationService {
	@Autowired
	private JobRepository jobRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ApplicationRepository appRepo;
	
	public String applyForJob(Long jobId, String email) {
	    // Check if user already applied for this job
	    boolean alreadyApplied = appRepo.existsByJobIdAndApplicantEmail(jobId, email);
	   
	    if (alreadyApplied) {
	        throw new RuntimeException("You have already applied for this job.");
	    }

	    Job job = jobRepo.findById(jobId)
	        .orElseThrow(() -> new RuntimeException("Job not found"));
	    User applicant = userRepo.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Application application = new Application();
	    application.setApplicant(applicant);
	    application.setJob(job);
	    application.setStatus(ApplicationStatus.APPLIED);
	    appRepo.save(application);
	    
	    return "Applied Successfully";
	}
	
	
	public List<ApplicationResponse> getMyApplications(String email) {
	    return appRepo.findByApplicantEmail(email).stream()
	        .map(app -> {
	            ApplicationResponse res = new ApplicationResponse();
	            res.setId(app.getId());
	            res.setJobTitle(app.getJob().getTitle());
	            res.setCompanyName(app.getJob().getPostedBy().getName());
	            res.setLocation(app.getJob().getLocation());
	            res.setStatus(app.getStatus());
	            res.setAppliedAt(app.getAppliedAt());
	            return res;
	        })
	        .collect(Collectors.toList());
	}
	
	public List<CompanyApplicationResponse> getApplicationsForJob(Long jobId) {
	    return appRepo.findByJobId(jobId).stream()
	        .map(app -> {
	            CompanyApplicationResponse res = new CompanyApplicationResponse();
	            res.setId(app.getId());
	            res.setJobTitle(app.getJob().getTitle());
	            res.setCompanyName(app.getJob().getPostedBy().getName());
	            res.setLocation(app.getJob().getLocation());
	            res.setApplicantName(app.getApplicant().getName());
	            res.setApplicantEmail(app.getApplicant().getEmail());
	            res.setStatus(app.getStatus());
	            res.setAppliedAt(app.getAppliedAt());
	            return res;
	        })
	        .collect(Collectors.toList());
	}

	public String updateStatus(Long appId, String status) {
	    Application app = appRepo.findById(appId)
	        .orElseThrow(() -> new RuntimeException("Application not found"));
	    app.setStatus(ApplicationStatus.valueOf(status));
	    appRepo.save(app);
	    return "Status updated to: " + status;
	}

	
	public String withdrawApplication(Long appId, String email) {
	    Application app = appRepo.findById(appId)
	        .orElseThrow(() -> new RuntimeException("Application not found"));
	    
	    // Check — sirf apni application withdraw kar sake
	    if (!app.getApplicant().getEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized");
	    }
	    
	    appRepo.delete(app);
	    return "Application withdrawn successfully";
	}
	
	
//	1. applyForJob(Long jobId, String email)
//	   → job dhundo by id
//	   → user dhundo by email
//	   → Application object banao
//	   → status = APPLIED set karo
//	   → save karo
//
//	2. getMyApplications(String email)
//	   → applicant ki sari applications
//	   → ApplicationResponse mein convert karo
//
//	3. getApplicationsForJob(Long jobId)
//	   → job ki sari applications
//	   → ApplicationResponse mein convert karo
//
//	4. updateStatus(Long appId, String status)
//	   → application dhundo by id
//	   → status update karo
//	   → save karo
}
