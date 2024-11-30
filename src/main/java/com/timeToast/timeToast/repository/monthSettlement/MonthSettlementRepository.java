package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;

import java.util.List;

public interface MonthSettlementRepository {
    List<MonthSettlement> findAllByMemberId(final long memberId);
    MonthSettlement getById(final long monthSettlementId);
}
