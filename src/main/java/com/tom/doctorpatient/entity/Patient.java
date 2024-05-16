package com.tom.doctorpatient.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Getter
@Setter
@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;
	private String FirstName;
	private String LastName;
	private String Age;
	private String Gender;
	private String ContactNumber;
	private String EmailAddress;
	private String city;
	private String state;
	private String userId;
	private String password;
	private String doctor;
	
}
