package com.timeToast.timeToast.repository.settlement;

import com.timeToast.timeToast.domain.settlement.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.time.LocalDate;

public interface SettlementJpaRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findAllByMemberId(final long memberId);
    List<Settlement> findAllByYearsMonth(final LocalDate yearMonth);
    List<Settlement> findAllByYearsMonthAndMemberId(final LocalDate yearMonth, final long memberId);
    List<Settlement> findAllByYearsMonthBetweenAndMemberId(LocalDate startDate, LocalDate endDate, long memberId);
}
