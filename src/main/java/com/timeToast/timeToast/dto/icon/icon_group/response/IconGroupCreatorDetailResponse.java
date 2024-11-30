package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupCreatorDetailResponse(

        IconGroupOrderedResponse iconGroupOrderedResponse,
        int price,
        String description,
        String creatorProfileUrl,
        String creatorNickname

) {
    public static IconGroupCreatorDetailResponse fromEntity(IconGroupOrderedResponse iconGroupOrderedResponse, IconGroup iconGroup, Member creator) {
        return IconGroupCreatorDetailResponse.builder()
                .iconGroupOrderedResponse(iconGroupOrderedResponse)
                .price(iconGroup.getPrice())
                .description(iconGroup.getDescription())
                .creatorProfileUrl(creator.getMemberProfileUrl())
                .creatorProfileUrl(creator.getNickname())
                .build();
    }
}