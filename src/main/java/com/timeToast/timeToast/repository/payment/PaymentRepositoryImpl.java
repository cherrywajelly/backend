package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository orderJpaRepository;

    public PaymentRepositoryImpl(final PaymentJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return orderJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(final long paymentId) {
        return orderJpaRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findAllByIconGroupId(final long iconGroupId) {
        return orderJpaRepository.findAllByItemId(iconGroupId);
    }
}
