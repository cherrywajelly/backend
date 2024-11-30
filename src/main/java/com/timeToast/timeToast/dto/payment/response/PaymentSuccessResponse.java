package com.timeToast.timeToast.dto.payment.response;

import lombok.Builder;

@Builder
public record PaymentSuccessResponse(
        long paymentId,
        String orderId,
        String orderName

) {
}
