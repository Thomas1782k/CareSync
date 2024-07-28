package com.tom.doctorpatient.service.paymentGateway;



public interface PaymentGateway {

    public String getPaymentLink(Long amount, Long patientId, String email);
}
