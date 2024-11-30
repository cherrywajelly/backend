package com.timeToast.timeToast.repository.monthSettlement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public class MonthSettlementRepositoryImpl implements MonthSettlementRepository{

    private MonthSettlementJpaRepository monthSettlementJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MonthSettlementRepositoryImpl(MonthSettlementJpaRepository monthSettlementJpaRepository, final JPAQueryFactory queryFactory) {
        this.monthSettlementJpaRepository = monthSettlementJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public MonthSettlement save(final MonthSettlement monthSettlement) {
        return monthSettlementJpaRepository.save(monthSettlement);
    }

    @Override
    public List<MonthSettlement> findAllByYearMonth(LocalDate yearMonth) {
        return monthSettlementJpaRepository.findAllByYearsMonth(yearMonth);
    }

}
