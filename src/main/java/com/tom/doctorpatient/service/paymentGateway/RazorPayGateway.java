package com.tom.doctorpatient.service.paymentGateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.springframework.stereotype.Service;

import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayException;

@Service
public class RazorPayGateway implements PaymentGateway {

    private RazorpayClient razorpayClient;
    public RazorPayGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String getPaymentLink(Long amount, Long patientId, String email) {
        //RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");
            paymentLinkRequest.put("expire_by", 1715029462);
            paymentLinkRequest.put("reference_id",patientId);
            paymentLinkRequest.put("description","Payment for order #" + patientId);
            JSONObject customer = new JSONObject();
            customer.put("contact","Thomas");
            customer.put("email",email);
            paymentLinkRequest.put("customer",customer);
            JSONObject notify = new JSONObject();
            paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            return payment.get("short_url").toString();
        }catch(Exception e){
            System.out.println(e);
            return "Something went wrong";
        }
    }
}
