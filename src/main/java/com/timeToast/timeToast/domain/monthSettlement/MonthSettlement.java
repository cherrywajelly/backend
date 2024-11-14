package com.timeToast.timeToast.domain.monthSettlement;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monthSettlement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthSettlement extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthSettlement_id")
    private long id;

    private long memberId;

    private long month;

    private long settlement;

    private SettlementState settlementState;

    @Builder
    public MonthSettlement(final long memberId, final long month, final long settlement, final SettlementState settlementState) {
        this.memberId = memberId;
        this.month = month;
        this.settlement = settlement;
        this.settlementState = settlementState;
    }

    public void updateSettlementState(final SettlementState settlementState) {
        this.settlementState = settlementState;
    }
}
