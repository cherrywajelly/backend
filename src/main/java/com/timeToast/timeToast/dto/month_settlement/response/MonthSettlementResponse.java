package com.timeToast.timeToast.dto.month_settlement.response;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.type.DateTime;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import lombok.Builder;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Builder
public record MonthSettlementResponse(
        long memberId,
        String nickname,
        String profileUrl,
        SettlementState settlementState

) {


}
