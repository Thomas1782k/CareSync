package com.tom.doctorpatient.dto;

import com.tom.doctorpatient.entity.PatientRecord;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class PatientDTO implements Serializable {

    private int pid;
    private String FirstName;
    private String LastName;
    private String Age;
    private String Gender;
    private String ContactNumber;
    private String EmailAddress;
    private String city;
    private String state;
    private PatientRecord PatientRecord;
}
