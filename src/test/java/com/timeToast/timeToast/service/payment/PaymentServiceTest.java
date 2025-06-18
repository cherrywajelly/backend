package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupMonthlyRevenue;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupMonthlyRevenues;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupSummaries;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupSummary;
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
                .orderId("order iconGroupId")
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

    @Override
    public IconGroupSummaries iconGroupSummary() {
        List<IconGroupSummary> iconGroupSummaries = new ArrayList<>();
        iconGroupSummaries.add(new IconGroupSummary("title", IconType.TOAST, 100));
        return new IconGroupSummaries(iconGroupSummaries);
    }

    @Override
    public IconGroupSummaries iconGroupSummaryByYearMonth(final int year, final int month) {
        List<IconGroupSummary> iconGroupSummaries = new ArrayList<>();
        iconGroupSummaries.add(new IconGroupSummary("title", IconType.TOAST, 100));
        return new IconGroupSummaries(iconGroupSummaries);
    }

    @Override
    public IconGroupMonthlyRevenues iconGroupMonthlyRevenue(final int year) {
        List<IconGroupMonthlyRevenue> iconGroupMonthlyRevenues = new ArrayList<>();
        iconGroupMonthlyRevenues.add(
                IconGroupMonthlyRevenue.builder()
                        .year(year)
                        .month(1)
                        .toastsRevenue(100L)
                        .jamsRevenue(100L)
                        .build()
        );
        return new IconGroupMonthlyRevenues(iconGroupMonthlyRevenues);
    }
}