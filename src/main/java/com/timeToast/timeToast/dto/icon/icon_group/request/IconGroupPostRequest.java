package com.timeToast.timeToast.dto.icon.icon_group.request;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;

public record IconGroupPostRequest (

        String name,
        int price,
        IconType iconType,
        IconBuiltin iconBuiltin,
        String description

) {
    public IconGroup toEntity(IconGroupPostRequest iconGroupPostRequest, final long memberId) {
        return IconGroup.builder()
                .name(iconGroupPostRequest.name)
                .price(iconGroupPostRequest.price)
                .iconType(iconGroupPostRequest.iconType)
                .iconBuiltin(iconGroupPostRequest.iconBuiltin)
                .description(description)
                .memberId(memberId)
                .build();
    }
}

