package com.example.payment.Implements;

import com.example.payment.EmailService;

public class EmailServiceImpl implements EmailService {
    @Override
    public void sendPaymentConfirmation(String email, double amount) {
        System.out.println("Email sent to " + email + " for payment of " + amount);
    }
}
