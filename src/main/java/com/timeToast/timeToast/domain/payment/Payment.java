package com.timeToast.timeToast.domain.payment;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private long iconGroupId;

    @Column(unique = true)
    private String orderId;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;

    private int amount;

    private long itemId;


    @Builder
    public Payment(final long memberId, final ItemType itemType, final PaymentState paymentState,
                   final int amount, final long itemId, final long iconGroupId) {
        this.memberId = memberId;
        this.itemType = itemType;
        this.paymentState = paymentState;
        this.itemId = itemId;
        this.amount = amount;
        this.iconGroupId = iconGroupId;
    }

    public void updateOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public void updatePaymentState(final PaymentState paymentState) {
        this.paymentState = paymentState;
    }
}

