package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PaymentRepository {

    Payment save(final Payment order);
    Optional<Payment> findById(final long orderId);
    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    Optional<Payment> findByOrderId(final String orderId);
    List<Payment> findAllByItemId(final long itemId);
//    List<Payment> findAllByIconGroupIdAndCreatedAtMonth(@Param("iconGroupId") Long iconGroupId, @Param("yearMonth") String yearMonth);
}
