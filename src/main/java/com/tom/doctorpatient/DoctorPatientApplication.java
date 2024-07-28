package com.tom.doctorpatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DoctorPatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorPatientApplication.class, args);
    }

}
