package com.HireFlow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.HireFlow.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

	List<Job> findByPostedByEmail(String email);
	@Query("SELECT j FROM Job j WHERE " +
		    "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
		    "(:skills IS NULL OR LOWER(j.skills) LIKE LOWER(CONCAT('%', :skills, '%')))")
		List<Job> searchJobs(@Param("location") String location, 
		                     @Param("skills") String skills);
}
