package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupOrderedResponse (
        String iconName,
        String thumbnailImageUrl,
        List<String> iconImageUrl,
        long orderCount,
        long income,
        IconState iconState
){
    public static IconGroupOrderedResponse of(final String iconName, final String thumbnailImageUrl, final List<String> iconImageUrl, final long orderCount, final long income, final IconState iconState) {
        return IconGroupOrderedResponse.builder()
                .iconName(iconName)
                .thumbnailImageUrl(thumbnailImageUrl)
                .iconImageUrl(iconImageUrl)
                .orderCount(orderCount)
                .income(income)
                .iconState(iconState)
                .build();
    }
}
