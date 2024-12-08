package com.timeToast.timeToast.dto.settlement.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SettlementCreatorInfoResponse(
        int year,
        int month,
        LocalDate settlementDate,
        long settlement,
        long revenue,
        long saleCount
) {
}
