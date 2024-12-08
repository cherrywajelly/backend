package com.timeToast.timeToast.dto.settlement.response;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import lombok.Builder;

@Builder
public record SettlementResponse(
        long memberId,
        String nickname,
        String profileUrl,
        SettlementState settlementState,
        long orderCount,
        long monthRevenue

) {

}
