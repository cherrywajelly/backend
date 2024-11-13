package com.timeToast.timeToast.dto.icon.icon.response;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import lombok.Builder;

@Builder
public record IconResponse(
        long iconId,
        String iconImageUrl
){

    public static IconResponse from(final Icon icon){
        return IconResponse.builder()
                .iconId(icon.getId())
                .iconImageUrl(icon.getIconImageUrl())
                .build();
    }
}
