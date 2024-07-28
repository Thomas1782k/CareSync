package com.tom.doctorpatient.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay_key_id}")
    private String apiKeyId;
    @Value("${razorpay_key_secret}")
    private String apiKeySecret;

    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(apiKeyId,apiKeySecret);
    }
}
