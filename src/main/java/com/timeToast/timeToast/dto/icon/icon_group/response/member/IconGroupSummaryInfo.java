package com.timeToast.timeToast.dto.icon.icon_group.response.member;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import lombok.Builder;

@Builder
public record IconGroupSummaryInfo(
        long id,
        String title,
        String creatorNickname,
        String thumbnailImageUrl,
        IconType iconType,
        int price

) {

    public static IconGroupSummaryInfo from(final IconGroup iconGroup, final String creatorNickname) {
        return IconGroupSummaryInfo.builder()
                .id(iconGroup.getId())
                .title(iconGroup.getName())
                .creatorNickname(creatorNickname)
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .iconType(iconGroup.getIconType())
                .price(iconGroup.getPrice())
                .build();
    }

}
