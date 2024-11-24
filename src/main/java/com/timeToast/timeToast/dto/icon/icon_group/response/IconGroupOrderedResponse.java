package com.timeToast.timeToast.dto.icon.icon_group.response;

import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupOrderedResponse (
        String iconName,
        List<String> iconImageUrl,
        long orderCount,
        long income
){
    public static IconGroupOrderedResponse of(final String iconName, final List<String> iconImageUrl, final long orderCount, final long income) {
        return IconGroupOrderedResponse.builder()
                .iconName(iconName)
                .iconImageUrl(iconImageUrl)
                .orderCount(orderCount)
                .income(income)
                .build();
    }
}
