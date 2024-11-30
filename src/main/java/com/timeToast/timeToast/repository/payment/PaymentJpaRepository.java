package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByIconGroupIdAndCreatedAtMonth(@Param("iconGroupId") Long iconGroupId, @Param("yearMonth") String yearMonth);
    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    Optional<Payment> findByOrderId(final String orderId);
}
