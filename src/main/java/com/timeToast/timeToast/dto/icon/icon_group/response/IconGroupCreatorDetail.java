package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupCreatorDetail(

        String iconName,
        int price,
        String description,
        List<String> iconImageUrl,
        String creatorProfileUrl,
        String creatorNickname,
        long orderCount,
        long income


) {
    public static IconGroupCreatorDetail fromEntity(IconGroup iconGroup, List<String> iconImageUrl, Member creator, long orderCount, long income) {
        return IconGroupCreatorDetail.builder()
                .iconName(iconGroup.getName())
                .price(iconGroup.getPrice())
                .description(iconGroup.getDescription())
                .iconImageUrl(iconImageUrl)
                .creatorProfileUrl(creator.getMemberProfileUrl())
                .creatorProfileUrl(creator.getNickname())
                .orderCount(orderCount)
                .income(income)
                .build();
    }
}