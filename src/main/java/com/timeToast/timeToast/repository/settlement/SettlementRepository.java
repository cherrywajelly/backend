package com.timeToast.timeToast.repository.settlement;

import com.timeToast.timeToast.domain.settlement.Settlement;
import com.timeToast.timeToast.dto.settlement.response.SettlementIcon;

import java.util.List;

import java.time.LocalDate;

public interface SettlementRepository {
    Settlement save(final Settlement settlement);
    List<Settlement> findAllByYearMonth(final LocalDate yearMonth);
    List<Settlement> findAllByYearMonthAndMemberId(final LocalDate yearMonth, final long memberId);
    List<SettlementIcon> findAllByYearMonthAndMemberIdToIcon(final LocalDate yearMonth,final long memberId);
    List<Settlement> findAllByMemberId(final long memberId);
    Settlement getById(final long monthSettlementId);
}
