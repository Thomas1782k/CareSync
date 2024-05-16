package com.tom.doctorpatient.repository;

import com.tom.doctorpatient.entity.Updateapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdateRepo extends JpaRepository<Updateapp, Integer> {

	List<Updateapp> findByDoctor(String did);
	
	Updateapp findByPid(int pid);
}
