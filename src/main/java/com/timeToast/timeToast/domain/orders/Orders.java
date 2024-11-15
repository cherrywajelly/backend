package com.timeToast.timeToast.domain.orders;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private long id;

    private long memberId;

    private long iconGroupId;

    private long payment;

    @Builder
    public Orders(final long memberId, final long iconGroupId, final long payment) {
        this.memberId = memberId;
        this.iconGroupId = iconGroupId;
        this.payment = payment;
    }
}

