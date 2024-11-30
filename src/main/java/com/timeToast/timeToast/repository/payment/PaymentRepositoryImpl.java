package com.timeToast.timeToast.repository.payment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.payment.PaymentDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.domain.payment.QPayment.payment;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(final PaymentJpaRepository paymentJpaRepository, final JPAQueryFactory queryFactory) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(final long paymentId) {
        return paymentJpaRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType) {
        return paymentJpaRepository.findAllByItemIdAndItemType(itemId, itemType);
    }

    @Override
    public List<PaymentDto> findAllByMonthlyPayments(final LocalDate start, final LocalDate end) {
        return queryFactory.select(
                Projections.constructor(
                        PaymentDto.class,
                        payment.itemId, payment.itemType, payment.count(),payment.amount.count()))
                .from(payment)
                .where(payment.createdAt.between(start.atStartOfDay(),end.atStartOfDay()), payment.paymentState.eq(PaymentState.SUCCESS))
                .groupBy(payment.itemId)
                .fetch();
    }

    @Override
    public List<Payment> findAllByItemId(final long itemId) {
        return paymentJpaRepository.findAllByItemId(itemId);
    }

//
//    @Override
//    @Query(
//            value = "SELECT * FROM Payment p WHERE p.item_id = :itemId AND DATE_FORMAT(p.created_at, '%Y-%m') = :yearMonth",
//            nativeQuery = true
//    )
//    public List<Payment> findAllByItemIdAndCreatedAtMonth(@Param("itemId") final long itemId, @Param("yearMonth") String yearMonth) {
//        return paymentJpaRepository.findAllByItemIdAndCreatedAtMonth(itemId, yearMonth);
//    }

    @Override
    public Optional<Payment> findByOrderId(final String orderId){
        return paymentJpaRepository.findByOrderId(orderId);
    }
}
