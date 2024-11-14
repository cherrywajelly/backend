package com.timeToast.timeToast.dto.icon.icon_group.response;

import lombok.Builder;

@Builder
public record IconGroupCreatorResponse (
        String iconImageUrl,
        String iconTitle
) {
    public static IconGroupCreatorResponse fromEntity(final String iconImageUrl, final String iconTitle) {
        return IconGroupCreatorResponse.builder()
                .iconImageUrl(iconImageUrl)
                .iconTitle(iconTitle)
                .build();
    }
}
