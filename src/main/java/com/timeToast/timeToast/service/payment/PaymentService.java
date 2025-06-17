package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupSummaries;
import com.timeToast.timeToast.dto.payment.request.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.request.PaymentSuccessRequest;
import com.timeToast.timeToast.dto.payment.response.*;

public interface PaymentService {
    PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest);
    PaymentSuccessResponse successPayment(final long memberId, final PaymentSuccessRequest paymentConfirmRequest);
    PaymentFailResponse failPayment(final long memberId, final String orderId);
    IconGroupSummaries iconGroupSummary();
    IconGroupSummaries iconGroupSummaryByYearMonth(final int year, final int month);
    PaymentsAdminResponses getIconPayments(final int page, final int size);
    PaymentsAdminResponses getPremiumPayments(final int page, final int size);
    PaymentDetailResponse getPaymentDetails(final long paymentId);
}
