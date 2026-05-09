package com.HireFlow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HireFlow.dto.JobRequest;
import com.HireFlow.dto.JobResponse;
import com.HireFlow.entity.Job;
import com.HireFlow.entity.User;
import com.HireFlow.repository.JobRepository;
import com.HireFlow.repository.UserRepo;

@Service
public class JobService {


	@Autowired
	private UserRepo usRepo;

	@Autowired
	private JobRepository jRepo;

	public String postJob(JobRequest req, String email) {
		User user = usRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		Job j = new Job();
		j.setTitle(req.getTitle());
		j.setDescription(req.getDescription());
		j.setLocation(req.getLocation());
		j.setSalary(req.getSalary());
		j.setSkills(req.getSkills());
		j.setPostedBy(user);
		jRepo.save(j);
		return "Job Posted successfully";
	}

	public List<JobResponse> getAllJobs() {
		return jRepo.findAll().stream().map(job -> {
			JobResponse res = new JobResponse();
			 res.setId(job.getId());
	            res.setTitle(job.getTitle());
	            res.setDescription(job.getDescription());
	            res.setLocation(job.getLocation());
	            res.setSalary(job.getSalary());
	            res.setSkills(job.getSkills());
	            res.setCompanyName(job.getPostedBy().getName());
	            res.setCompanyEmail(job.getPostedBy().getEmail());
	            res.setCreatedAt(job.getCreatedAt());
	            return res;
		}).collect(Collectors.toList());
		
	}

	public JobResponse getJobById(Long id) {
		Job job = jRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
		 JobResponse res = new JobResponse();
		    res.setId(job.getId());
		    res.setTitle(job.getTitle());
		    res.setDescription(job.getDescription());
		    res.setLocation(job.getLocation());
		    res.setSalary(job.getSalary());
		    res.setSkills(job.getSkills());
		    res.setCompanyName(job.getPostedBy().getName());
		    res.setCompanyEmail(job.getPostedBy().getEmail());
		    res.setCreatedAt(job.getCreatedAt());
		    return res;
	}

	public List<JobResponse> getMyPostedJobs(String email) {
	    return jRepo.findByPostedByEmail(email).stream()
	        .map(job -> {
	            JobResponse res = new JobResponse();
	            res.setId(job.getId());
	            res.setTitle(job.getTitle());
	            res.setDescription(job.getDescription());
	            res.setLocation(job.getLocation());
	            res.setSalary(job.getSalary());
	            res.setSkills(job.getSkills());
	            res.setCompanyName(job.getPostedBy().getName());
	            res.setCompanyEmail(job.getPostedBy().getEmail());
	            res.setCreatedAt(job.getCreatedAt());
	            return res;
	        })
	        .collect(Collectors.toList());
	}
	
	public String deleteJob(Long jobId, String email) {
	    Job job = jRepo.findById(jobId)
	        .orElseThrow(() -> new RuntimeException("Job not found"));

	    // Sirf apni job delete kar sake
	    if (!job.getPostedBy().getEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized");
	    }

	    jRepo.delete(job);
	    return "Job deleted successfully";
	}
	
	public String updateJob(Long jobId, JobRequest request, String email) {
	    Job job = jRepo.findById(jobId)
	        .orElseThrow(() -> new RuntimeException("Job not found"));

	    // Sirf apni job edit kar sake
	    if (!job.getPostedBy().getEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized");
	    }

	    job.setTitle(request.getTitle());
	    job.setDescription(request.getDescription());
	    job.setLocation(request.getLocation());
	    job.setSalary(request.getSalary());
	    job.setSkills(request.getSkills());
	    jRepo.save(job);

	    return "Job updated successfully";
	}
	
	public List<JobResponse> searchJobs(String location, String skills) {
	    return jRepo.searchJobs(location, skills).stream()
	        .map(job -> {
	            JobResponse res = new JobResponse();
	            res.setId(job.getId());
	            res.setTitle(job.getTitle());
	            res.setDescription(job.getDescription());
	            res.setLocation(job.getLocation());
	            res.setSalary(job.getSalary());
	            res.setSkills(job.getSkills());
	            res.setCompanyName(job.getPostedBy().getName());
	            res.setCompanyEmail(job.getPostedBy().getEmail());
	            res.setCreatedAt(job.getCreatedAt());
	            return res;
	        })
	        .collect(Collectors.toList());
	}
}
