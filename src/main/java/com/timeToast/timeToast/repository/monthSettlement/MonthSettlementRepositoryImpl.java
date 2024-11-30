package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import org.springframework.stereotype.Repository;

@Repository
public class MonthSettlementRepositoryImpl implements MonthSettlementRepository{

    private MonthSettlementJpaRepository monthSettlementJpaRepository;

    @Override
    public MonthSettlement save(final MonthSettlement monthSettlement) {
        return monthSettlementJpaRepository.save(monthSettlement);
    }


}
