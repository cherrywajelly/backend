package com.timeToast.timeToast.dto.icon.icon_group.response.creator;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

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
                .creatorNickname(creator.getNickname())
                .build();
    }
}