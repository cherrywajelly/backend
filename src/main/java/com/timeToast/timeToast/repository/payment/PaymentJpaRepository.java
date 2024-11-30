package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    Optional<Payment> findByOrderId(final String orderId);
}
