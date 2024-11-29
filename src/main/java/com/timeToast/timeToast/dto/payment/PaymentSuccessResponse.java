package com.timeToast.timeToast.dto.payment;

import lombok.Builder;

@Builder
public record PaymentSuccessResponse(
        long paymentId,
        String orderId,
        String orderName

) {
}
