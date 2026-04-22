package com.HireFlow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HireFlow.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

}
