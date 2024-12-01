package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.dto.payment.request.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.request.PaymentSuccessRequest;
import com.timeToast.timeToast.dto.payment.response.PaymentFailResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSaveResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSuccessResponse;

public interface PaymentService {
    PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest);
    PaymentSuccessResponse successPayment(final long memberId, final PaymentSuccessRequest paymentConfirmRequest);
    PaymentFailResponse failPayment(final long memberId, final String orderId);
}
