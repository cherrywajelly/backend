package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import lombok.Builder;

@Builder
public record IconGroupMarketResponse(
        long iconGroupId,
        String title,
        String thumbnailImageUrl,
        String creatorNickname,
        IconType iconType,
        boolean isBuy
) {
}
