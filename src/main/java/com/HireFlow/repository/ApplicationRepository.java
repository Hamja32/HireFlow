package com.HireFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.HireFlow.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{

	public List<Application>findByApplicantEmail(String email);
	public List<Application>findByJobId(Long jobid);
	public boolean existsByJobIdAndApplicantEmail(Long jobId, String email);
	public void deleteByIdAndApplicantEmail(Long id, String email);
//	1. findByApplicantEmail(String email) 
//	   → seeker ki sari applications
//
//	2. findByJobId(Long jobId)
//	   → ek job pe saari applications
}
