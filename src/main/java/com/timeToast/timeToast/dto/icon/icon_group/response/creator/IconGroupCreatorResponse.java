package com.timeToast.timeToast.dto.icon.icon_group.response.creator;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupCreatorResponse (
        long iconGroupId,
        //thumnail
        String iconImageUrl,
        String iconTitle,
        IconState iconState
) {
    public static IconGroupCreatorResponse fromEntity(final IconGroup iconGroup) {
        return IconGroupCreatorResponse.builder()
                .iconGroupId(iconGroup.getId())
                .iconImageUrl(iconGroup.getThumbnailImageUrl())
                .iconTitle(iconGroup.getName())
                .iconState(iconGroup.getIconState())
                .build();
    }
}
