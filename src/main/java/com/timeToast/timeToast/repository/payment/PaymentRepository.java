package com.timeToast.timeToast.repository.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.payment.PaymentDto;
import com.timeToast.timeToast.dto.payment.IconGroupPaymentSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface PaymentRepository {
    Payment save(final Payment order);
    Optional<Payment> findById(final long paymentId);
    Optional<Payment> findByOrderId(final String orderId);
    Optional<Payment> findRecentPremiumByMemberId(final long memberId);
    List<Payment> findAllByItemIdAndItemType(final long itemId, final ItemType itemType);
    List<PaymentDto> findAllByMonthlyPayments(final LocalDate start, final LocalDate end);
    List<Payment> findAllByItemId(final long itemId);
    Page<Payment> findAll(final Pageable pageable);
    List<IconGroupPaymentSummaryDto> findPaymentSummaryDto();
    List<IconGroupPaymentSummaryDto> findIconGroupPaymentSummaryDtoByYearMonth(final int year, final int month);
    Long findPremiumPaymentSummaryDtoByYearMonth(final int year, final int month);

    List<Payment> findByMemberId(final long memberId);
}
