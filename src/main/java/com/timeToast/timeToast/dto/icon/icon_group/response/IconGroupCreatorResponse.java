package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import lombok.Builder;

@Builder
public record IconGroupCreatorResponse (
        long iconGroupId,
        String iconImageUrl,
        String iconTitle,
        IconState iconState
) {
    public static IconGroupCreatorResponse fromEntity(final long iconGroupId, final String iconImageUrl, final String iconTitle, final IconState iconState) {
        return IconGroupCreatorResponse.builder()
                .iconGroupId(iconGroupId)
                .iconImageUrl(iconImageUrl)
                .iconTitle(iconTitle)
                .iconState(iconState)
                .build();
    }
}
