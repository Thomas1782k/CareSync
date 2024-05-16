package com.tom.doctorpatient.repository;
import com.tom.doctorpatient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepo extends JpaRepository<Patient, Integer> {
	
	Patient findByPid(int pid);
	Patient findByUserId(String userId);
	//Patient findByEmailAddress(String EmailAddress);
}