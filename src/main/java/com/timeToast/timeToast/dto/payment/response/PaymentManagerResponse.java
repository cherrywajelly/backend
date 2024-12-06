package com.timeToast.timeToast.dto.payment.response;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PaymentManagerResponse (
        int amount,
        PaymentState paymentState,
        String orderId,
        ItemType itemType,
        String itemTypeData,
        LocalDate createdAt,
        String nickname,
        List<String> images,
        LocalDate expiredDate
) {
    public static PaymentManagerResponse from(Payment payment, String itemTypeData, String nickname, List<String> images) {
        return PaymentManagerResponse.builder()
                .amount(payment.getAmount())
                .paymentState(payment.getPaymentState())
                .orderId(payment.getOrderId())
                .itemType(payment.getItemType())
                .itemTypeData(itemTypeData)
                .createdAt(payment.getCreatedAt().toLocalDate())
                .nickname(nickname)
                .images(images)
                .expiredDate(payment.getExpiredDate())
                .build();
    }
}
