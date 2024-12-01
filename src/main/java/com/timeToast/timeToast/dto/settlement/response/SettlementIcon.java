package com.timeToast.timeToast.dto.settlement.response;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import lombok.Builder;

@Builder
public record SettlementIcon(
        String title,
        long revenue,
        int salesCount,
        SettlementState settlementState
) {

}
