package com.HireFlow.dto;
import java.time.LocalDateTime;

import com.HireFlow.enums.ApplicationStatus;
public class CompanyApplicationResponse {
		private Long id;
		private String jobTitle;
		private String companyName;
		private String location;
		private ApplicationStatus status;
		private LocalDateTime appliedAt;
		
		private String applicantName;
		private String applicantEmail;
		public String getApplicantName() {
			return applicantName;
		}
		public void setApplicantName(String applicantName) {
			this.applicantName = applicantName;
		}
		public String getApplicantEmail() {
			return applicantEmail;
		}
		public void setApplicantEmail(String applicantEmail) {
			this.applicantEmail = applicantEmail;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public ApplicationStatus getStatus() {
			return status;
		}
		public void setStatus(ApplicationStatus status) {
			this.status = status;
		}
		public LocalDateTime getAppliedAt() {
			return appliedAt;
		}
		public void setAppliedAt(LocalDateTime appliedAt) {
			this.appliedAt = appliedAt;
		}
		
		
		
		
		
//		id           → Long
//		jobTitle     → String
//		companyName  → String
//		location     → String
//		status       → ApplicationStatus
//		appliedAt    → LocalDateTime
	}


