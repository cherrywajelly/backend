package com.timeToast.timeToast.dto.month_settlement.response;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import lombok.Builder;

@Builder
public record MonthSettlementResponse(
        long memberId,
        String nickname,
        String profileUrl,
        SettlementState settlementState
) {

}
