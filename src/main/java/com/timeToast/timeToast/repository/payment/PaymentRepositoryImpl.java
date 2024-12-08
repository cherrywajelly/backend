package com.timeToast.timeToast.repository.payment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.payment.PaymentDto;
import com.timeToast.timeToast.dto.payment.IconGroupPaymentSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.domain.icon.icon_group.QIconGroup.iconGroup;
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
                                iconGroup.memberId,payment.itemId, payment.itemType, payment.count(), payment.amount.sum()))
                .from(payment)
                .join(iconGroup).on(payment.itemId.eq(iconGroup.id))
                .where(payment.createdAt.between(start.atStartOfDay(), end.atStartOfDay()),
                        payment.paymentState.eq(PaymentState.SUCCESS),
                        payment.itemType.eq(ItemType.ICON))
                .groupBy(iconGroup.memberId, payment.itemId)
                .fetch();
    }

    @Override
    public List<Payment> findAllByItemId(final long itemId) {
        return paymentJpaRepository.findAllByItemId(itemId);
    }

    @Override
    public Page<Payment> findAll(final Pageable pageable) {
        return paymentJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Payment> findByOrderId(final String orderId){
        return paymentJpaRepository.findByOrderId(orderId);
    }

    @Override
    public Optional<Payment> findRecentPremiumByMemberId(final long memberId) {
        return paymentJpaRepository.findAllByMemberIdAndItemTypeAndPaymentStateOrderByExpiredDateDesc(memberId,ItemType.PREMIUM, PaymentState.SUCCESS).stream().findFirst();
    }

    @Override
    public List<IconGroupPaymentSummaryDto> findPaymentSummaryDto() {
        return queryFactory.select(Projections.constructor(
                IconGroupPaymentSummaryDto.class, payment.itemId,iconGroup.name, iconGroup.iconType, iconGroup.price, payment.count()))
                .from(payment)
                .join(iconGroup).on(payment.itemId.eq(iconGroup.id))
                .where(payment.itemType.eq(ItemType.ICON))
                .groupBy(payment.itemId)
                .fetch();
    }

    @Override
    public List<IconGroupPaymentSummaryDto> findIconGroupPaymentSummaryDtoByYearMonth(final int year, final int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return queryFactory.select(Projections.constructor(
                        IconGroupPaymentSummaryDto.class, payment.itemId,iconGroup.name, iconGroup.iconType, iconGroup.price, payment.count()))
                .from(payment)
                .join(iconGroup).on(payment.itemId.eq(iconGroup.id))
                .where(payment.itemType.eq(ItemType.ICON).and(payment.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59)))
                        .and(payment.paymentState.eq(PaymentState.SUCCESS)))
                .groupBy(payment.itemId)
                .fetch();
    }

    @Override
    public Long findPremiumPaymentSummaryDtoByYearMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return queryFactory.select(payment.count())
                .from(payment)
                .where(payment.itemType.eq(ItemType.PREMIUM)
                        .and(payment.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59)))
                        .and(payment.paymentState.eq(PaymentState.SUCCESS)))
                .groupBy(payment.itemId)
                .fetchOne();
    }

    @Override
    public Page<Payment> findAllByItemType(final ItemType itemType, final Pageable pageable) {
        return paymentJpaRepository.findAllByItemType(itemType, pageable);
    }


    @Override
    public List<Payment> findByMemberId(final long memberId) {
        return paymentJpaRepository.findByMemberId(memberId);
    }
}
