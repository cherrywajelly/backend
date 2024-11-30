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

import java.time.LocalDate;
import java.time.YearMonth;

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

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private long iconGroupId;

    @Column(nullable = false)
    private LocalDate yearsMonth;

    @Column(nullable = false)
    private int salesCount;

    private long revenue;

    private long settlement;

    @Enumerated(EnumType.STRING)
    private SettlementState settlementState;

    @Column(nullable = true)
    private LocalDate settlementDate;

    @Builder
    public MonthSettlement(final long memberId, final long iconGroupId, final LocalDate yearMonth, final int salesCount,
                           final long revenue, final long settlement, final SettlementState settlementState) {
        this.memberId = memberId;
        this.iconGroupId = iconGroupId;
        this.yearsMonth = yearMonth;
        this.salesCount = salesCount;
        this.revenue = revenue;
        this.settlement = settlement;
        this.settlementState = settlementState;
    }

    public void updateSettlementState(final SettlementState settlementState) {
        this.settlementState = settlementState;
    }
    public void updateSettlementDate(final LocalDate settlementDate) {this.settlementDate = settlementDate;}
}
