package com.tom.doctorpatient.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

import javax.persistence.Entity;

//@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long payId;
    private String patientName;
    private long patientId;
    private long amount;
    private String email;

}
