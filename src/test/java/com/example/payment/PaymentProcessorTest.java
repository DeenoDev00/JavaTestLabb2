package com.example.payment;

import org.assertj.core.internal.IterableElementComparisonStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentProcessorTest {
private PaymentService paymentService;
private DatabaseService databaseService;
private EmailService emailService;
private PaymentProcessor paymentProcessor;

@BeforeEach
    void setUp() {
    paymentService = mock(PaymentService.class);
    databaseService = mock(DatabaseService.class);
    emailService = mock(EmailService.class);
    paymentProcessor = new PaymentProcessor(paymentService, databaseService, emailService);

}

    @Test
    void processPayment_ShouldSavePaymentAndSendEmail_WhenSuccessfull() {
    double amount = 100.00;
    when(paymentService.processPayment(amount))
            .thenReturn(new PaymentApiResponse(true));

    boolean result  = paymentProcessor.processPayment(amount);

    assertThat(result).isTrue();
    verify(databaseService).savePayment(amount, "SUCCESS");
    verify(emailService).sendPaymentConfirmation("user@example.com", amount);

    }

    @Test
    void processPayment_ShouldNotSavePaymentOrSendEmail_WhenFailure() {
    double amount = 100.00;
    when(paymentService.processPayment(amount))
    .thenReturn(new PaymentApiResponse(false));

    boolean result  = paymentProcessor.processPayment(amount);

    assertThat(result).isFalse();
        verify(databaseService, never()).savePayment(anyDouble(), anyString());
        verify(emailService, never()).sendPaymentConfirmation(anyString(), anyDouble());
    }

}