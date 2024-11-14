package com.timeToast.timeToast.dto.icon.icon_group.response;

import lombok.Builder;

@Builder
public record IconGroupCreatorResponse (
        long iconGroupId,
        String iconImageUrl,
        String iconTitle
) {
    public static IconGroupCreatorResponse fromEntity(final long iconGroupId, final String iconImageUrl, final String iconTitle) {
        return IconGroupCreatorResponse.builder()
                .iconGroupId(iconGroupId)
                .iconImageUrl(iconImageUrl)
                .iconTitle(iconTitle)
                .build();
    }
}
