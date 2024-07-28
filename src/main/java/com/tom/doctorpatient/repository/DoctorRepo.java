package com.tom.doctorpatient.repository;

import com.tom.doctorpatient.entity.Doctor;
import com.tom.doctorpatient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepo extends JpaRepository<Doctor, String>
{
	Doctor findByUserId(String userId);
	Doctor findByDid(int did);

	@Query(value = "select * from Doctor  where first_name like %:name%  or last_name like %:name%", nativeQuery = true)
	Page<Doctor> searchByDoctor(String name, Pageable pageable);
	Page<Doctor> findAll(Pageable pageable);
}
