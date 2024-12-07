package com.timeToast.timeToast.dto.payment;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;

public record PaymentSummaryDto(
        long itemId,
        String itemName,
        IconType iconType,
        long totalCount
) {
}
