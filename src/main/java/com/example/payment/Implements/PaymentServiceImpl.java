package com.example.payment.Implements;

import com.example.payment.PaymentApiResponse;
import com.example.payment.PaymentService;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentApiResponse processPayment(double amount) {
        // Simulerar anrop till extern betaltjänst
        return new PaymentApiResponse(true);
    }
}

