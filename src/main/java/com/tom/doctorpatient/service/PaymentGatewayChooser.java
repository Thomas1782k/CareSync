package com.tom.doctorpatient.service;

import com.tom.doctorpatient.service.paymentGateway.PaymentGateway;
import com.tom.doctorpatient.service.paymentGateway.StripeGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayChooser {

    private StripeGateway stripeGateway;

    public PaymentGatewayChooser( StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    public PaymentGateway getPaymentGateway() {
        return stripeGateway;
    }
}
