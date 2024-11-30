package com.timeToast.timeToast.domain.month_settlement;

import com.google.type.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.time.LocalDate;
import java.time.YearMonth;

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

    private Date yearMonth;

    private long revenue;

    private long settlement;

    private SettlementState settlementState;

    private DateTime settlementDate;

    @Builder
    public MonthSettlement(final long memberId, final Date yearMonth, final long settlement, final SettlementState settlementState) {
        this.memberId = memberId;
        this.yearMonth = yearMonth;
        this.settlement = settlement;
        this.settlementState = settlementState;
    }

    public void updateSettlementState(final SettlementState settlementState) {
        this.settlementState = settlementState;
    }

    public void updateSettlementDate(final DateTime settlementDate) { this.settlementDate = settlementDate; }
}
