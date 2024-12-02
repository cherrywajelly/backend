package com.timeToast.timeToast.dto.payment.request;

public record PaymentSuccessRequest(
        String paymentKey,
        String orderId,
        int amount
) {
}
