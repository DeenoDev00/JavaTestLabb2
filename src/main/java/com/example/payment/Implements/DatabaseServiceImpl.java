package com.example.payment.Implements;

import com.example.payment.DatabaseService;

public class DatabaseServiceImpl implements DatabaseService {
    @Override
    public void savePayment(double amount, String status) {
        System.out.println("Payment saved: " + amount + " - Status: " + status);
    }
}

