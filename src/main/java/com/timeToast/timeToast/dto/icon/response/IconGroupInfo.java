package com.timeToast.timeToast.dto.icon.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupInfo(
        long iconGroupId,
        String title,
        String creatorNickname,
        String thumbnailImageUrl,
        String description,
        IconType iconType,
        IconState iconState,
        int price

) {

    public static IconGroupInfo from(final IconGroup iconGroup, final String creatorNickname) {
        return IconGroupInfo.builder()
                .iconGroupId(iconGroup.getId())
                .title(iconGroup.getName())
                .creatorNickname(creatorNickname)
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .description(iconGroup.getDescription())
                .iconType(iconGroup.getIconType())
                .iconState(iconGroup.getIconState())
                .price(iconGroup.getPrice())
                .build();
    }

}
