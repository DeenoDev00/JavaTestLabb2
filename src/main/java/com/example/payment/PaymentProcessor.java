package com.example.payment;

import com.example.payment.DatabaseService;
import com.example.payment.EmailService;
import com.example.payment.PaymentApiResponse;
import com.example.payment.PaymentService;

public class PaymentProcessor {
    private final PaymentService paymentService;
    private final DatabaseService databaseService;
    private final EmailService emailService;

    public PaymentProcessor(PaymentService paymentService, DatabaseService databaseService, EmailService emailService) {
        this.paymentService = paymentService;
        this.databaseService = databaseService;
        this.emailService = emailService;
    }

    public boolean processPayment(double amount) {
        PaymentApiResponse response = paymentService.processPayment(amount);

        if (response.isSuccess()) {
            databaseService.savePayment(amount, "SUCCESS");
            emailService.sendPaymentConfirmation("user@example.com", amount);
        }

        return response.isSuccess();
    }
}

