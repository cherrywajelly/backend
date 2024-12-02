package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
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
    public static IconGroupOrderedResponse of(final IconGroup iconGroup, final List<String> iconImageUrl, final long orderCount, final long income) {
        return IconGroupOrderedResponse.builder()
                .iconName(iconGroup.getName())
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .iconImageUrl(iconImageUrl)
                .orderCount(orderCount)
                .income(income)
                .iconState(iconGroup.getIconState())
                .build();
    }
}
