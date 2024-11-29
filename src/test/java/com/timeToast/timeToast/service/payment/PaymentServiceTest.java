package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.dto.payment.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest implements PaymentService {


    @Override
    public PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest) {
        return PaymentSaveResponse.builder()
                .paymentId(1L)
                .orderId("dsinjfn8dfyjwn")
                .orderName("order name")
                .successUrl("success url")
                .failUrl("fail url")
                .customerEmail("email").build();
    }

    @Override
    public PaymentSuccessResponse successPayment(final long memberId, final PaymentSuccessRequest paymentConfirmRequest) {
        return PaymentSuccessResponse.builder()
                .paymentId(1L)
                .orderId("dsinjfn8dfyjwn")
                .orderName("order name")
                .build();
    }

    @Override
    public PaymentFailResponse failPayment(final long memberId,final String orderId) {
        return new PaymentFailResponse(1L, "diosfhjuih","실패했습니다");
    }
}