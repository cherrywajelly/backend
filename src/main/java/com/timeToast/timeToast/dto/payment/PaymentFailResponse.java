package com.timeToast.timeToast.dto.payment;

public record PaymentFailResponse(
        long paymentId,
        String orderId,
        String errorMessage
) {
}
