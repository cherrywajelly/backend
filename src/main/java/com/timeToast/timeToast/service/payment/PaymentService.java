package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.dto.payment.*;

public interface PaymentService {
    PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest);
    PaymentSuccessResponse successPayment(final long memberId, final long paymentId, final PaymentSuccessRequest paymentConfirmRequest);
    PaymentFailResponse failPayment(final long memberId, final long paymentId);
}
