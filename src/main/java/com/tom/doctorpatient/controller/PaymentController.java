package com.tom.doctorpatient.controller;


import com.tom.doctorpatient.dto.PaymentDTO;
import com.tom.doctorpatient.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/pay")
    public String initiatePayment(@RequestBody PaymentDTO payment){
    return paymentService.initiatePayment(payment.getAmount(),payment.getPatientId(),payment.getEmail());
    }
}
