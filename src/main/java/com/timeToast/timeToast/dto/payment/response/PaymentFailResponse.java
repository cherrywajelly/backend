package com.timeToast.timeToast.dto.payment.response;

public record PaymentFailResponse(
        long paymentId,
        String orderId,
        String errorMessage
) {
}
