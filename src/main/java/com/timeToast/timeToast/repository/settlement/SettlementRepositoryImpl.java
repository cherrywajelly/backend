package com.timeToast.timeToast.repository.settlement;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.settlement.Settlement;
import com.timeToast.timeToast.dto.settlement.response.SettlementIcon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.timeToast.timeToast.domain.settlement.QSettlement.settlement;
import static com.timeToast.timeToast.domain.icon.icon_group.QIconGroup.iconGroup;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.SETTLEMENT_NOT_FOUND;

@Repository
public class SettlementRepositoryImpl implements SettlementRepository {

    private final SettlementJpaRepository settlementJpaRepository;
    private final JPAQueryFactory queryFactory;

    public SettlementRepositoryImpl(final SettlementJpaRepository settlementJpaRepository, final JPAQueryFactory queryFactory) {
        this.settlementJpaRepository = settlementJpaRepository;
        this.queryFactory = queryFactory;
    }
    @Override
    public List<Settlement> findAllByMemberId(final long memberId) {
        return settlementJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public Settlement getById(final long monthSettlementId){
        return settlementJpaRepository.findById(monthSettlementId).orElseThrow(() -> new NotFoundException(SETTLEMENT_NOT_FOUND.getMessage()));
    }
    @Override
    public Settlement save(final Settlement settlement) {
        return settlementJpaRepository.save(settlement);
    }

    @Override
    public List<Settlement> findAllByYearMonth(final LocalDate yearMonth) {
        return settlementJpaRepository.findAllByYearsMonth(yearMonth);
    }

    @Override
    public List<Settlement> findAllByYearMonthAndMemberId(final LocalDate yearMonth, final long memberId) {
        return settlementJpaRepository.findAllByYearsMonthAndMemberId(yearMonth, memberId);
    }

    @Override
    public List<SettlementIcon> findAllByYearMonthAndMemberIdToIcon(final LocalDate yearMonth, final long memberId) {
       return queryFactory.select(
               Projections.constructor(
                       SettlementIcon.class, iconGroup.name, settlement.revenue, settlement.salesCount, settlement.settlementState))
               .from(settlement)
               .join(iconGroup).on(settlement.iconGroupId.eq(iconGroup.id))
               .where(settlement.yearsMonth.eq(yearMonth), settlement.memberId.eq(memberId))
               .fetch();
//        return null;
    }

    @Override
    public List<Settlement> findAllByYearsMonthBetweenAndMemberId(LocalDate startDate, LocalDate endDate, long memberId) {
        return settlementJpaRepository.findAllByYearsMonthBetweenAndMemberId(startDate, endDate, memberId);
    }

}
