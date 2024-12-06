package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAll (final Pageable pageable);
    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    Optional<Payment> findByOrderId(final String orderId);
    List<Payment> findAllByItemId(final long itemId);
    List<Payment> findAllByMemberIdAndItemTypeAndPaymentStateOrderByExpiredDateDesc(final long memberId, final ItemType itemType, final PaymentState paymentState);
    List<Payment> findByMemberId(final long memberId);
}
