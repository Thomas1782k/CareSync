package com.tom.doctorpatient.service;


import com.tom.doctorpatient.service.paymentGateway.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGatewayChooser paymentGatewayChooser;

    public PaymentService(PaymentGatewayChooser paymentGatewayChooser) {
        this.paymentGatewayChooser = paymentGatewayChooser;
    }

    public String initiatePayment( Long amount, Long patientId, String email){

        PaymentGateway paymentGateway= paymentGatewayChooser.getPaymentGateway();
        return paymentGateway.getPaymentLink(amount,patientId ,email);
    }
}
