package com.timeToast.timeToast.domain.month_settlement;

import com.google.type.DateTime;
import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "month_settlement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthSettlement extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "month_settlement_id")
    private long id;

    private long memberId;

    private LocalDate yearMonth;

    private long revenue;

    private long settlement;

    private SettlementState settlementState;

    private DateTime settlementDate;

    @Builder
    public MonthSettlement(final long memberId, final LocalDate yearMonth, final long revenue,
                           final long settlement, final SettlementState settlementState, final DateTime settlementDate) {
        this.memberId = memberId;
        this.yearMonth = yearMonth;
        this.revenue = revenue;
        this.settlement = settlement;
        this.settlementState = settlementState;
        this.settlementDate = settlementDate;
    }

    public void updateSettlementState(final SettlementState settlementState) {
        this.settlementState = settlementState;
    }
}
