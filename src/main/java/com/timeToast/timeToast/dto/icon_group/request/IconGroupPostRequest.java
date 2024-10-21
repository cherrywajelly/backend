package com.timeToast.timeToast.dto.icon_group.request;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.icon.request.IconPostRequest;

import java.util.Set;

public record IconGroupPostRequest (

        String name,
        long price,
        IconType iconType

) {
    public IconGroup toEntity(IconGroupPostRequest iconGroupPostRequest, Member member) {
        return IconGroup.builder()
                .name(iconGroupPostRequest.name)
                .price(iconGroupPostRequest.price)
                .iconType(iconGroupPostRequest.iconType)
                .member(member)
                .build();
    }
}

