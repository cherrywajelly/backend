package com.timeToast.timeToast.service.monthSettlement;

import com.timeToast.timeToast.dto.month_settlement.request.MonthSettlementRequest;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;

public interface MonthSettlementService {
    MonthSettlementResponses getMonthSettlementByYearMonth(final MonthSettlementRequest monthSettlementRequest);
}
