package com.timeToast.timeToast.dto.payment;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;

public record IconGroupPaymentSummaryDto(
        long itemId,
        String itemName,
        IconType iconType,
        int price,
        long totalCount
) {
}
