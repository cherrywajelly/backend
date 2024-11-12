package com.timeToast.timeToast.dto.premium.response;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;
import lombok.Builder;

@Builder
public record PremiumResponse(
        Long premiumId,
        PremiumType premiumType,
        int price,
        int count,
        String description

) {

    public static PremiumResponse from(final Premium premium){
        return PremiumResponse.builder()
                .premiumId(premium.getId())
                .premiumType(premium.getPremiumType())
                .price(premium.getPrice())
                .count(premium.getCount())
                .description(premium.getDescription())
                .build();
    }
}
