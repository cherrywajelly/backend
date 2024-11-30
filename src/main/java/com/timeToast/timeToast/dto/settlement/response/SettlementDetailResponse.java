package com.timeToast.timeToast.dto.settlement.response;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import lombok.Builder;

import java.util.List;

@Builder
public record SettlementDetailResponse(
        int year,
        int month,
        String creatorNickname,
        long salesIconCount,
        long totalRevenue,
        long settlement,
        Bank bank,
        String accountNumber,
        SettlementState settlementState,
        List<SettlementIcon> settlementIcons
) {
}