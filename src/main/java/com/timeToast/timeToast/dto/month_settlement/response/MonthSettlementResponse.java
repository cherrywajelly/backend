package com.timeToast.timeToast.dto.month_settlement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.monthSettlement.MonthSettlement;
import lombok.Builder;

import java.time.LocalDate;
import java.time.YearMonth;

@Builder
public record MonthSettlementResponse(
        long monthSettlementId,
        YearMonth yearMonth,
        long settlement,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate settlementDate
) {
    public static MonthSettlementResponse from(MonthSettlement monthSettlement) {
        return MonthSettlementResponse.builder()
                .monthSettlementId(monthSettlement.getId())
                .yearMonth(monthSettlement.getMonth())
                .settlement(monthSettlement.getSettlement())
                .settlementDate(monthSettlement.getSettlementDate())
                .build();
    }
}
