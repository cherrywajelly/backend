package com.timeToast.timeToast.dto.month_settlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;

public record MonthSettlementDto(
        long memberId,
        SettlementState settlementState
) {
}
