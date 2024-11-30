package com.timeToast.timeToast.service.month_settlement;

import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementDetailResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;

public interface MonthSettlementService {
    MonthSettlementResponses getMonthSettlements(final long creatorId);
//    MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId);
}
