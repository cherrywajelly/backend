package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.dto.payment.request.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.request.PaymentSuccessRequest;
import com.timeToast.timeToast.dto.payment.response.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public PaymentFailResponse failPayment(final long memberId, final String orderId) {
        return new PaymentFailResponse(1L, "diosfhjuih","실패했습니다");
    }

    @Override
    public PaymentsAdminResponses getIconPayments(int page, int size) {
        List<PaymentsAdminResponse> paymentsAdminResponses = new ArrayList<>();

        paymentsAdminResponses.add(
                PaymentsAdminResponse.builder()
                        .paymentId(1L)
                        .itemType(ItemType.ICON)
                        .nickname("nickname")
                        .itemName("item name")
                        .createdAt(LocalDate.now())
                        .amount(1100)
                        .paymentState(PaymentState.SUCCESS)
                        .build()
        );

        return new PaymentsAdminResponses(paymentsAdminResponses);
    }

    @Override
    public PaymentsAdminResponses getPremiumPayments(int page, int size) {
        List<PaymentsAdminResponse> paymentsAdminResponses = new ArrayList<>();

        paymentsAdminResponses.add(
                PaymentsAdminResponse.builder()
                        .paymentId(1L)
                        .itemType(ItemType.ICON)
                        .nickname("nickname")
                        .createdAt(LocalDate.now())
                        .amount(1100)
                        .paymentState(PaymentState.SUCCESS)
                        .expiredDate(LocalDate.now().plusDays(1))
                        .build()
        );

        return new PaymentsAdminResponses(paymentsAdminResponses);
    }

    @Override
    public PaymentDetailResponse getPaymentDetails(long paymentId) {
        return PaymentDetailResponse.builder()
                .orderId("order id")
                .nickname("nickname")
                .itemType(ItemType.ICON)
                .itemName("item name")
                .amount(1000)
                .paymentState(PaymentState.WAITING)
                .createdAt(LocalDate.now())
                .expiredDate(LocalDate.now())
                .iconThumbnailImageUrl("icon thumbnail url")
                .build();
    }
}