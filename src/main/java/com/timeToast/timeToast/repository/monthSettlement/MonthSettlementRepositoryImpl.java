package com.timeToast.timeToast.repository.monthSettlement;

import com.timeToast.timeToast.domain.monthSettlement.MonthSettlement;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.SETTLEMENT_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MonthSettlementRepositoryImpl implements MonthSettlementRepository{
    private final MonthSettlementJpaRepository monthSettlementJpaRepository;

    @Override
    public List<MonthSettlement> findAllByMemberId(final long memberId) {
        return monthSettlementJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public MonthSettlement getById(final long monthSettlementId){
        return monthSettlementJpaRepository.findById(monthSettlementId).orElseThrow(() -> new NotFoundException(SETTLEMENT_NOT_FOUND.getMessage()));
    }
}
