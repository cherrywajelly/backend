package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.payment.Payment;

import java.util.Optional;
import java.util.List;

public interface PaymentRepository {

    Payment save(final Payment order);
    Optional<Payment> findById(final long orderId);
    List<Payment> findAllByIconGroupId(final long iconGroupId);
}
