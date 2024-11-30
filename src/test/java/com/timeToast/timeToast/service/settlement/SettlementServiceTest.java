package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.dto.settlement.request.SettlementApprovalRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementDetailRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SettlementServiceTest implements SettlementService{

    @Override
    public SettlementCreatorInfoResponse approvalSettlement(final SettlementApprovalRequest settlementApprovalRequest) {
        return SettlementCreatorInfoResponse.builder()
                .year(1)
                .month(1)
                .settlementDate(LocalDate.now())
                .build();
    }

    @Override
    public SettlementCreatorInfoResponses getSettlementByYearMonthByCreator(final long memberId) {
        List<SettlementCreatorInfoResponse> settlementCreatorInfoResponses = new ArrayList<>();
        settlementCreatorInfoResponses.add(
                SettlementCreatorInfoResponse.builder()
                        .year(1)
                        .month(1)
                        .settlementDate(LocalDate.now())
                        .build());
        return new SettlementCreatorInfoResponses(settlementCreatorInfoResponses);
    }

    @Override
    public SettlementResponses getSettlementByYearMonth(final SettlementRequest settlementRequest) {
        List<SettlementResponse> settlementResponses = new ArrayList<>();
        settlementResponses.add(
                SettlementResponse.builder()
                        .memberId(1L)
                        .nickname("nickname")
                        .profileUrl("profileUrl")
                        .settlementState(SettlementState.WAITING)
                        .build());
        return new SettlementResponses(settlementResponses);
    }

    @Override
    public SettlementDetailResponse getAllSettlementByCreator(final long memberId, final SettlementRequest settlementRequest) {
        return SettlementDetailResponse.builder()
                .year(1)
                .month(1)
                .creatorNickname("creatorNickname")
                .salesIconCount(1L)
                .totalRevenue(1L)
                .settlement(1L)
                .bank(Bank.CZNBKRSE)
                .accountNumber("accountNumber")
                .settlementState(SettlementState.APPROVAL)
                .settlementIcons(List.of(new SettlementIcon("title", 1L, 1, SettlementState.APPROVAL)))
                .build();
    }

    @Override
    public SettlementDetailResponse getSettlementByYearMonthAndCreator(final SettlementDetailRequest settlementDetailRequest) {
        return SettlementDetailResponse.builder()
                .year(1)
                .month(1)
                .creatorNickname("creatorNickname")
                .salesIconCount(1L)
                .totalRevenue(1L)
                .settlement(1L)
                .bank(Bank.CZNBKRSE)
                .accountNumber("accountNumber")
                .settlementState(SettlementState.APPROVAL)
                .settlementIcons(List.of(new SettlementIcon("title", 1L, 1, SettlementState.APPROVAL)))
                .build();
    }
}