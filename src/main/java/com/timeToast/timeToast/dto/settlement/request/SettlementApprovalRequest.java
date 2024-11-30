package com.timeToast.timeToast.dto.settlement.request;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;

public record SettlementApprovalRequest(
        long creatorId,
        int year,
        int month,
        SettlementState settlementState
) {
}
