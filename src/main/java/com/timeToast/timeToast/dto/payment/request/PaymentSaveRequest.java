package com.timeToast.timeToast.dto.payment.request;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;


public record PaymentSaveRequest(
        long itemId,
        int amount,
        ItemType itemType,
        String successUrl,
        String failUrl
) {

    public static Payment to(final long memberId, final PaymentSaveRequest paymentSaveRequest) {
        return Payment.builder()
                .memberId(memberId)
                .itemType(paymentSaveRequest.itemType)
                .paymentState(PaymentState.WAITING)
                .itemId(paymentSaveRequest.itemId)
                .amount(paymentSaveRequest.amount)
                .build();
    }

}
