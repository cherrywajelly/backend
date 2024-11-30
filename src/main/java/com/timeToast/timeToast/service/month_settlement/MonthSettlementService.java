package com.timeToast.timeToast.service.month_settlement;

import com.timeToast.timeToast.dto.month_settlement.request.MonthSettlementRequest;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementCreatorResponses;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;

public interface MonthSettlementService {
    MonthSettlementCreatorResponses getCreatorMonthSettlements(final long creatorId);
    MonthSettlementResponses getMonthSettlementByYearMonth(final MonthSettlementRequest monthSettlementRequest);
//    MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId);
}
