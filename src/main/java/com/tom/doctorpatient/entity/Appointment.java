package com.tom.doctorpatient.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int appid;
	//@OneToMany(mappedBy = "patient")
	private int pid;
	private String pname;
	private LocalDateTime appointmentDate;
	private String symptoms;
	private String doctor;
	
	
	public Appointment() {
		super();
	}

	public Appointment(int pid, String pname, LocalDateTime appointmentDate, String symptoms, String doctor) {
		super();

		this.pid=pid;
		this.pname = pname;
		this.appointmentDate = appointmentDate;
		this.symptoms = symptoms;
		this.doctor = doctor;
	}

	public LocalDateTime getDate() {
		return appointmentDate;
	}
	public void setDate(LocalDateTime date) {
		this.appointmentDate = date;
	}


}
