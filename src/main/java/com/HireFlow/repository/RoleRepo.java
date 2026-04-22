package com.HireFlow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HireFlow.entity.Role;
import com.HireFlow.enums.RoleName;

public interface RoleRepo extends JpaRepository<Role, Long>{
	 Optional<Role> findByName(RoleName name);
}
