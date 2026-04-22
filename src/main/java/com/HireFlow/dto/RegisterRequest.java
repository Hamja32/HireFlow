package com.HireFlow.dto;

import com.HireFlow.entity.Role;


public class RegisterRequest {
//	RegisterRequest.java   (name, email, password, role)

	private String name;
	private String email;
	private String password;
	private Role roles;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRoles() {
		return roles;
	}
	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
	
}
