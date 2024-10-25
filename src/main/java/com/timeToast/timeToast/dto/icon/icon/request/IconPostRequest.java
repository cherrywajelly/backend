package com.timeToast.timeToast.dto.icon.icon.request;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;

public record IconPostRequest(
        String icon_image_url
) {
    public Icon toEntity(IconPostRequest iconPostRequest, final long iconGroupId){
        return Icon.builder()
                .icon_image_url(iconPostRequest.icon_image_url)
                .iconGroupId(iconGroupId)
                .build();
    }
}
