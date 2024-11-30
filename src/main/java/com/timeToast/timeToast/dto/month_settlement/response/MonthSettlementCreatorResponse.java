package com.timeToast.timeToast.dto.month_settlement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MonthSettlementCreatorResponse(
        long monthSettlementId,
        @JsonFormat(pattern = "yyyy-MM")
        LocalDate yearMonth,
        long settlement,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate settlementDate
) {

        public static MonthSettlementCreatorResponse from(final MonthSettlement monthSettlement) {
        return MonthSettlementCreatorResponse.builder()
                .monthSettlementId(monthSettlement.getId())
                .yearMonth(monthSettlement.getYearsMonth())
                .settlement(monthSettlement.getSettlement())
                .settlementDate(monthSettlement.getSettlementDate())
                .build();
    }
}
