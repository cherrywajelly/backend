package com.timeToast.timeToast.dto.settlement.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SettlementCreatorMonthInfoResponse (
        int year,
        int month,
        LocalDate settlementDate
) {
}
