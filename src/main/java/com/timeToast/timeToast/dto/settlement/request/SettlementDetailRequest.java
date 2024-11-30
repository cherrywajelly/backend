package com.timeToast.timeToast.dto.settlement.request;

public record SettlementDetailRequest(
        long creatorId,
        int year,
        int month
) {
}
