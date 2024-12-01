package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.dto.settlement.request.SettlementApprovalRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementDetailRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.*;

public interface SettlementService {
//    SettlementCreatorResponses getCreatorMonthSettlements(final long creatorId);
    SettlementCreatorInfoResponse approvalSettlement(final long creatorId, SettlementRequest settlementRequest);
    SettlementCreatorInfoResponses getSettlementByYearMonthByCreator(final long memberId);
    SettlementResponses getSettlementByYearMonth(final int year, final int month);
    SettlementDetailResponse getAllSettlementByCreator(final long memberId, final int year, final int month);
    SettlementDetailResponse getSettlementByYearMonthAndCreator(final long creatorId,final int year, final int month);
//    MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId);
}
