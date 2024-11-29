package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
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
    public List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType) {
        return orderJpaRepository.findAllByItemIdAndItemType(itemId, itemType);
    }

    @Override
    public Optional<Payment> findByOrderId(final String orderId){
        return orderJpaRepository.findByOrderId(orderId);
    }
}
