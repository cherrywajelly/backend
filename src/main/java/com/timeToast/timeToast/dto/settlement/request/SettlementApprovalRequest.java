package com.timeToast.timeToast.dto.settlement.request;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;

public record SettlementApprovalRequest(
        int year,
        int month
) {
}
