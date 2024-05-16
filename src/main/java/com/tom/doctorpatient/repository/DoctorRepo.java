package com.tom.doctorpatient.repository;

import com.tom.doctorpatient.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, String>
{
	Doctor findByUserId(String userId);
	Doctor findByDid(int did);
}
