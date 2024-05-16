package com.tom.doctorpatient.repository;

import com.tom.doctorpatient.entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRecordRepo extends JpaRepository<PatientRecord, Integer>
{

	PatientRecord findByPid(int pid);
		
}
