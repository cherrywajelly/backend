package com.timeToast.timeToast.dto.payment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentsAdminResponse(

        long paymentId,

        String nickname,

        String itemName,

        ItemType itemType,

        long amount,

        PaymentState paymentState,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate expiredDate
) {
}
