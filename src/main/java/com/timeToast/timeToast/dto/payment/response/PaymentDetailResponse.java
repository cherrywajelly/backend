package com.timeToast.timeToast.dto.payment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentDetailResponse(

        String orderId,

        String nickname,

        ItemType itemType,

        String itemName,

        long amount,

        PaymentState paymentState,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate expiredDate,

        String iconThumbnailImageUrl

) {
}
