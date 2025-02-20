package com.example.payment;

import com.example.payment.DatabaseService;
import com.example.payment.EmailService;
import com.example.payment.PaymentApiResponse;
import com.example.payment.PaymentService;

/**
 * Hanterar betalningar genom att anropa en betaltjänst, spara betalningen i databasen
 * och skicka ett bekräftelsemejl.
 *
 * <p><b>Refaktoreringsändringar:</b></p>
 * <ul>
 *   <li>Tog bort hårdkodade beroenden och ersatte dem med interface.</li>
 *   <li>Införde PaymentService, DatabaseService och EmailService för bättre struktur.</li>
 *   <li>Använder Dependency Injection så klassen kan testas enklare.</li>
 * </ul>
 *
 * <p><b>Fördelar:</b></p>
 * <ul>
 *   <li>Gör koden mer flexibel och lättare att ändra.</li>
 *   <li>Underlättar testning eftersom vi kan mocka tjänsterna.</li>
 *   <li>Bättre separation av logik så varje klass gör en sak.</li>
 * </ul>
 */

public class PaymentProcessor {
    private final PaymentService paymentService;
    private final DatabaseService databaseService;
    private final EmailService emailService;

    /**
     * Skapar en instans av PaymentProcessor med de tjänster som behövs.
     *
     * @param paymentService   Hanterar själva betalningen.
     * @param databaseService  Sparar betalningsinformationen.
     * @param emailService     Skickar betalningsbekräftelser.
     */

    public PaymentProcessor(PaymentService paymentService, DatabaseService databaseService, EmailService emailService) {
        this.paymentService = paymentService;
        this.databaseService = databaseService;
        this.emailService = emailService;
    }

    /**
     * Behandlar en betalning genom att anropa betaltjänsten, lagra i databasen och skicka mejl.
     *
     * @param amount Beloppet att betala.
     * @return true om betalningen gick igenom, annars false.
     */

    public boolean processPayment(double amount) {
        PaymentApiResponse response = paymentService.processPayment(amount);

        if (response.isSuccess()) {
            databaseService.savePayment(amount, "SUCCESS");
            emailService.sendPaymentConfirmation("user@example.com", amount);
        }

        return response.isSuccess();
    }
}

