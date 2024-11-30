package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    @Query("SELECT o FROM Payment o WHERE o.iconGroupId = :iconGroupId AND FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m') = :yearMonth")
//    public List<Payment> findAllByIconGroupIdAndCreatedAtMonth(@Param("iconGroupId") Long iconGroupId, @Param("yearMonth") String yearMonth) {
//        return orderJpaRepository.findAllByIconGroupIdAndCreatedAtMonth(iconGroupId, yearMonth);
//    }

    @Override
    public Optional<Payment> findByOrderId(final String orderId){
        return orderJpaRepository.findByOrderId(orderId);
    }
}
