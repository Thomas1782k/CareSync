package com.tom.doctorpatient.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PatientRecord 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recordId;
	private int pid;

	private String prescription;

	//BMI Validations
	private String bmi;
	
	//Glucose Monitoring
	private LocalDateTime timeOfGlucose;
	private String bloodGlucose;
	
	
	//Blood Count
	private LocalDateTime timeOfBlood;
	private String rbcCount;
	private String wbcCount;
	private String plateletCount;
	
	
	//Activity Tracker
	private LocalDateTime dateOfActivity;
	private String activity;
	private LocalDateTime timeOfWorkOut;
	
	
	//Cholesterol Monitor
	private LocalDateTime timeOfCholesterol;
	private String cholesterol;
	

	//Pressure Monitor
	//@Temporal(TemporalType.TIME)
	private LocalDateTime timeOfPressure;
	private String pressure;
	
	
	//Thyroid Monitor
	private LocalDateTime timeOfThyroid;
	private String thyroid;
	
	
	//Calories Tracker
	private LocalDateTime dateOfCalories;
	private String calories;
	private LocalDateTime timeOfCaloriesIntake;
	
	
	//Diet Tracker
	private LocalDateTime dateOfDiet;
	private String diet;
	private LocalDateTime timeOfDietIntake;


}
