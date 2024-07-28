package com.tom.doctorpatient.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Getter
@Setter
@Entity
public class Doctor implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int did;
	private String FirstName;
	private String LastName;
	private String Age;
	private String Gender;
	private String ContactNumber;
	private String Qualification;
	private String Speciality;
	private String address;
	private String city;
	private String state;
	private String EmailAddress;
	private String userId;
	private String Password;
	


}
