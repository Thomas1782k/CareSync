package com.tom.doctorpatient.repository;

import com.tom.doctorpatient.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, String> {

	Admin findByUserId(String userId);
	
//	@Query(nativeQuery = true,value="select concat(FirstName,LastName) from doctor where did> id ")
//	List<Doctor> allDoctor(@Param("id") Set<String> names);
	
}
