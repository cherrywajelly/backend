package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthSettlementJpaRepository extends JpaRepository<MonthSettlement, Long> {
    List<MonthSettlement> findAllByMemberId(final long memberId);
}
