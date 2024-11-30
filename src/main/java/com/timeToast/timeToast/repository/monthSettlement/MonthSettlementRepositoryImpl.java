package com.timeToast.timeToast.repository.monthSettlement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.SETTLEMENT_NOT_FOUND;

@Repository
public class MonthSettlementRepositoryImpl implements MonthSettlementRepository{

    private MonthSettlementJpaRepository monthSettlementJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MonthSettlementRepositoryImpl(MonthSettlementJpaRepository monthSettlementJpaRepository, final JPAQueryFactory queryFactory) {
        this.monthSettlementJpaRepository = monthSettlementJpaRepository;
        this.queryFactory = queryFactory;
    }
    @Override
    public List<MonthSettlement> findAllByMemberId(final long memberId) {
        return monthSettlementJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public MonthSettlement getById(final long monthSettlementId){
        return monthSettlementJpaRepository.findById(monthSettlementId).orElseThrow(() -> new NotFoundException(SETTLEMENT_NOT_FOUND.getMessage()));
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
