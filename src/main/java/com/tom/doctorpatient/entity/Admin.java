package com.tom.doctorpatient.entity;


import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class Admin {

	@Id
	private String userId;
	private String password;
	private String name;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [userId=" + userId + ", password=" + password + "]";
	}
	
}
