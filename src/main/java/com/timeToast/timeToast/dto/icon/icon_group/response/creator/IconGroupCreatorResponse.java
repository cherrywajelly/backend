package com.timeToast.timeToast.dto.icon.icon_group.response.creator;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupCreatorResponse (
        long iconGroupId,
        String iconImageUrl,
        String iconTitle,
        IconState iconState,
        long orderCount,
        long totalRevenue

) {
    public static IconGroupCreatorResponse fromEntity(final IconGroup iconGroup, final int orderCount, final long totalRevenue) {
        return IconGroupCreatorResponse.builder()
                .iconGroupId(iconGroup.getId())
                .iconImageUrl(iconGroup.getThumbnailImageUrl())
                .iconTitle(iconGroup.getName())
                .iconState(iconGroup.getIconState())
                .orderCount(orderCount)
                .totalRevenue(totalRevenue)
                .build();
    }
}
