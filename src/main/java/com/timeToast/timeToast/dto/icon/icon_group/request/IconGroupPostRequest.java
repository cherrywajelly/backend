package com.timeToast.timeToast.dto.icon.icon_group.request;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;

public record IconGroupPostRequest (

        String name,
        long price,
        IconType iconType

) {
    public IconGroup toEntity(IconGroupPostRequest iconGroupPostRequest, final long memberId) {
        return IconGroup.builder()
                .name(iconGroupPostRequest.name)
                .price(iconGroupPostRequest.price)
                .iconType(iconGroupPostRequest.iconType)
                .memberId(memberId)
                .build();
    }
}

