package com.timeToast.timeToast.dto.premium.response;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;
import lombok.Builder;

@Builder
public record PremiumInfoResponse(
        Long premiumId,
        PremiumType premiumType,
        int price,
        int count,
        String description

) {

    public static PremiumInfoResponse from(final Premium premium){
        return PremiumInfoResponse.builder()
                .premiumId(premium.getId())
                .premiumType(premium.getPremiumType())
                .price(premium.getPrice())
                .count(premium.getCount())
                .description(premium.getDescription())
                .build();
    }
}
