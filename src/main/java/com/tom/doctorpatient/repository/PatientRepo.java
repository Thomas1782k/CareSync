package com.tom.doctorpatient.repository;
import com.tom.doctorpatient.dto.PatientDTO;
import com.tom.doctorpatient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PatientRepo extends JpaRepository<Patient, Integer> {
	
	Patient findByPid(int pid);
	Patient findByUserId(String userId);
	//Patient findByEmailAddress(String EmailAddress);

	@Query(value = "select * from Patient  where first_name like %:name%  or last_name like %:name%", nativeQuery = true)
	Page<Patient> searchByPatient(String name, Pageable pageable);
	Page<Patient> findAll(Pageable pageable);
}