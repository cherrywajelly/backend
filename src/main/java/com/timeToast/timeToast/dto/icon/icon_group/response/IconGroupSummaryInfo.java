package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupSummaryInfo(
        long iconGroupId,
        String title,
        String creatorNickname,
        String thumbnailImageUrl,
        String description,
        IconType iconType,
        int price

) {

    public static IconGroupSummaryInfo from(final IconGroup iconGroup, final String creatorNickname) {
        return IconGroupSummaryInfo.builder()
                .iconGroupId(iconGroup.getId())
                .title(iconGroup.getName())
                .creatorNickname(creatorNickname)
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .description(iconGroup.getDescription())
                .iconType(iconGroup.getIconType())
                .price(iconGroup.getPrice())
                .build();
    }

}
