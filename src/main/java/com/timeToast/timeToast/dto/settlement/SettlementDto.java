package com.timeToast.timeToast.dto.settlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;

public record SettlementDto(
        long memberId,
        SettlementState settlementState
) {
}
