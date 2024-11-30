package com.timeToast.timeToast.dto.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;

public record PaymentDto(
        long memberId,
        long itemId,
        ItemType itemType,
        long salesCount,
        long salesRevenue
) {
}
