package com.tom.doctorpatient.service.paymentGateway;

import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//483
@Service
public class StripeGateway implements PaymentGateway{

    @Value("${stripe_key}")
     String api;
    @Override
    public String getPaymentLink(Long amount, Long patientId, String email) {
        Stripe.apiKey = api;
        ProductCreateParams prod =
                ProductCreateParams.builder()
                                   .setName("Doctor Appointment")
                                   .build();
        try {
            Product product = Product.create(prod);
        }catch(Exception e) {
            System.out.print(e.getMessage());
        }

        PriceCreateParams pric =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(amount)
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName(prod.getName()).build()
                        )
                        .build();
        Price price=null;
        try{
         price = Price.create(pric);
        }catch(Exception e) {
            System.out.print(e.getMessage());
            return "no price";
        }

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .build();

        System.out.println(params);
        PaymentLink paymentLink = null;
        try {
            paymentLink = PaymentLink.create(params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "no link";
        }

        return paymentLink.getUrl().toString();
    }
}
