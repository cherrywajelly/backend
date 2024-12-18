package com.timeToast.timeToast.dto.payment.response;

import lombok.Builder;

@Builder
public record PaymentSaveResponse(
        long paymentId,
        String orderId,
        String orderName,
        String successUrl,
        String failUrl,
        String customerEmail
) {


}
