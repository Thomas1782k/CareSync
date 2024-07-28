package com.tom.doctorpatient.dto;

import com.tom.doctorpatient.entity.Appointment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class DoctorDTO implements Serializable {

    private int did;
    private String FirstName;
    private String LastName;
    private String Age;
    private String Gender;
    private String ContactNumber;
    private String Qualification;
    private String Speciality;
    private String address;
    private String city;
    private String state;
    private String EmailAddress;
    private Appointment appointment;
}
