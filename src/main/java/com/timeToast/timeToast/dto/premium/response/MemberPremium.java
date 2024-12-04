package com.timeToast.timeToast.dto.premium.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MemberPremium(
        Long premiumId,
        PremiumType premiumType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate expiredDate
) {

    public static MemberPremium from(final Premium premium, final LocalDate expiredDate) {
        return MemberPremium.builder()
                .premiumId(premium.getId())
                .premiumType(premium.getPremiumType())
                .expiredDate(expiredDate)
                .build();
    }
}
