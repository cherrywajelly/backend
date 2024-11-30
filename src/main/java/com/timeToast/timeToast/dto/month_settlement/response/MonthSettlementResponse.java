package com.timeToast.timeToast.dto.month_settlement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.type.DateTime;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import lombok.Builder;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Builder
public record MonthSettlementResponse(
        long monthSettlementId,
        Date yearMonth,
        long settlement,
        @JsonFormat(pattern = "yyyy-MM-dd")
        DateTime settlementDate
) {
    public static MonthSettlementResponse from(MonthSettlement monthSettlement) {
        return MonthSettlementResponse.builder()
                .monthSettlementId(monthSettlement.getId())
                .yearMonth(monthSettlement.getYearMonth())
                .settlement(monthSettlement.getSettlement())
                .settlementDate(monthSettlement.getSettlementDate())
                .build();
    }
}
