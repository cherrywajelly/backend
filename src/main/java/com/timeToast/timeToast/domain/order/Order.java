package com.timeToast.timeToast.domain.order;

import com.google.type.DateTime;
import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    private long memberId;

    private long iconGroupId;

    private long payment;

    @Builder
    public Order(final long memberId, final long iconGroupId, final long payment) {
        this.memberId = memberId;
        this.iconGroupId = iconGroupId;
        this.payment = payment;
    }
}

