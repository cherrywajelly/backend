package com.timeToast.timeToast.dto.icon.icon_group.response.admin;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupAdminResponse(
        long iconGroupId,
        String title,
        String thumbnailUrl,
        IconType iconType,
        IconState iconState,
        String nickname
) {
    public static IconGroupAdminResponse from(final IconGroup iconGroup, final String nickname) {
        return IconGroupAdminResponse.builder()
                .iconGroupId(iconGroup.getId())
                .title(iconGroup.getName())
                .thumbnailUrl(iconGroup.getThumbnailImageUrl())
                .iconType(iconGroup.getIconType())
                .iconState(iconGroup.getIconState())
                .nickname(nickname)
                .build();
    }
}
