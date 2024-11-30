package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.dto.settlement.request.SettlementDetailRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.*;

public interface SettlementService {
//    SettlementCreatorResponses getCreatorMonthSettlements(final long creatorId);
    SettlementCreatorInfoResponse approvalSettlement(final SettlementDetailRequest settlementDetailRequest);
    SettlementCreatorInfoResponses getSettlementByYearMonthByCreator(final long memberId);
    SettlementResponses getSettlementByYearMonth(final SettlementRequest settlementRequest);
    SettlementDetailResponse getAllSettlementByCreator(final long memberId, final SettlementRequest settlementRequest);
    SettlementDetailResponse getSettlementByYearMonthAndCreator(final SettlementDetailRequest settlementDetailRequest);
//    MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId);
}
