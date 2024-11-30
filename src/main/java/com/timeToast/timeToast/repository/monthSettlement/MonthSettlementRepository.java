package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import java.util.List;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MonthSettlementRepository {
    MonthSettlement save(MonthSettlement monthSettlement);
    List<MonthSettlement> findAllByYearMonth(LocalDate yearMonth);
    List<MonthSettlement> findAllByMemberId(final long memberId);
    MonthSettlement getById(final long monthSettlementId);
}
