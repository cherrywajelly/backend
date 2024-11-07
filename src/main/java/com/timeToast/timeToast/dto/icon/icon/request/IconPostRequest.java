package com.timeToast.timeToast.dto.icon.icon.request;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;

public record IconPostRequest(
        String iconImageUrl
) {
    public Icon toEntity(IconPostRequest iconPostRequest, final long iconGroupId){
        return Icon.builder()
                .iconImageUrl(iconPostRequest.iconImageUrl)
                .iconGroupId(iconGroupId)
                .build();
    }
}
