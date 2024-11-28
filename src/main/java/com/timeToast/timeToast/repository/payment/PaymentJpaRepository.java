package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByIconGroupId(final long iconGroupId);
}
