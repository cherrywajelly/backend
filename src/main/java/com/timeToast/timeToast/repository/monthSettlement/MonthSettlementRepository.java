package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MonthSettlementRepository {
    MonthSettlement save(MonthSettlement monthSettlement);
    List<MonthSettlement> findAllByYearMonth(LocalDate yearMonth);
}
