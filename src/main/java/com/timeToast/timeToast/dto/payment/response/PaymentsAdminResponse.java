package com.timeToast.timeToast.dto.payment.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentsAdminResponse(

        long paymentId,

        String nickname,

        String itemName,

        ItemType itemType,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt
) {
}
