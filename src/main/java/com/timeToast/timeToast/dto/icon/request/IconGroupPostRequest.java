package com.timeToast.timeToast.dto.icon.request;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;

public record IconGroupPostRequest (
        String name,
        int price,
        IconType iconType,
        String description

) {
    public IconGroup toEntity(IconGroupPostRequest iconGroupPostRequest, final long memberId) {
        return IconGroup.builder()
                .name(iconGroupPostRequest.name)
                .price(iconGroupPostRequest.price)
                .iconType(iconGroupPostRequest.iconType)
                .iconBuiltin(IconBuiltin.NONBUILTIN)
                .description(iconGroupPostRequest.description)
                .memberId(memberId)
                .iconState(IconState.WAITING)
                .build();
    }
}

