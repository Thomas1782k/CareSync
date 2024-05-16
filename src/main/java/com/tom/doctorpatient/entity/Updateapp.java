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
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Updateapp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;
	private int pid;
	private String pname;
	private LocalDateTime appointmentDate;
	private String symptoms;
	private String doctor;
	private String prescription;


	public Updateapp(int id, String pname, LocalDateTime appointmentDate, String symp, String doc, String prescription) {
	  //this.uid = id;
	  this.pid = id;
	  this.pname = pname;
	  this.appointmentDate = appointmentDate;
	  this.symptoms = symp;
	  this.doctor = doc;
	  this.prescription = prescription;

	}
	public LocalDateTime getDate() {
		return appointmentDate;
	}
}
