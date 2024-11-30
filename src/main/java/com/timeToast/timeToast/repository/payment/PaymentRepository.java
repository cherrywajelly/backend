package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.payment.PaymentDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface PaymentRepository {
    Payment save(final Payment order);
    Optional<Payment> findById(final long orderId);
    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    List<PaymentDto> findAllByMonthlyPayments(final LocalDate start, final LocalDate end);
    Optional<Payment> findByOrderId(final String orderId);
}
