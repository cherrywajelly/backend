package com.timeToast.timeToast.dto.settlement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.settlement.Settlement;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SettlementCreatorResponse(
        long monthSettlementId,
        @JsonFormat(pattern = "yyyy-MM")
        LocalDate yearMonth,
        long settlement,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate settlementDate
) {

        public static SettlementCreatorResponse from(final Settlement settlement) {
        return SettlementCreatorResponse.builder()
                .monthSettlementId(settlement.getId())
                .yearMonth(settlement.getYearsMonth())
                .settlement(settlement.getSettlements())
                .settlementDate(settlement.getSettlementDate())
                .build();
    }
}
