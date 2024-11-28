package com.timeToast.timeToast.dto.payment;

public record PaymentSuccessRequest(
        String paymentKey,
        String orderId,
        int amount
) {
}
