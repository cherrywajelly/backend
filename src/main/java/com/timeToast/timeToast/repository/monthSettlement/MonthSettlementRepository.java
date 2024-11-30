package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import java.util.List;

public interface MonthSettlementRepository {
    MonthSettlement save(MonthSettlement monthSettlement);
    List<MonthSettlement> findAllByMemberId(final long memberId);
    MonthSettlement getById(final long monthSettlementId);
}
