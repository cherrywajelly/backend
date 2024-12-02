package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupInfoResponse(
        long iconGroupId,
        String title,
        String thumbnailUrl,
        IconType iconType,
        IconState iconState
) {

    public static IconGroupInfoResponse from(final IconGroup iconGroup) {
        return IconGroupInfoResponse.builder()
                .iconGroupId(iconGroup.getId())
                .title(iconGroup.getName())
                .thumbnailUrl(iconGroup.getThumbnailImageUrl())
                .iconType(iconGroup.getIconType())
                .iconState(iconGroup.getIconState())
                .build();
    }
}
