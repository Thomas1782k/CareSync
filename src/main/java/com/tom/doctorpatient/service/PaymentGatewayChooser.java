package com.tom.doctorpatient.service;

import com.tom.doctorpatient.service.paymentGateway.PaymentGateway;
import com.tom.doctorpatient.service.paymentGateway.RazorPayGateway;
import com.tom.doctorpatient.service.paymentGateway.StripeGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayChooser {

    private RazorPayGateway razorPayGateway;
    private StripeGateway stripeGateway;

    public PaymentGatewayChooser(RazorPayGateway razorPayGateway, StripeGateway stripeGateway) {
        this.razorPayGateway = razorPayGateway;
        this.stripeGateway = stripeGateway;
    }

    public PaymentGateway getPaymentGateway() {
        return stripeGateway;
    }
}
