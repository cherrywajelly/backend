package com.timeToast.timeToast.dto.month_settlement.response;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import lombok.Builder;

@Builder
public record MonthSettlementDetailResponse(
        String creatorProfileUrl,
        String creatorNickname,
        long selledIconCount,
        long settlement,

        IconGroupOrderedResponses iconGroupOrderedResponses
) {
    public static MonthSettlementDetailResponse from(final Member member, final long selledIconCount, final long settlement, IconGroupOrderedResponses iconGroupOrderedResponses) {
        return MonthSettlementDetailResponse.builder()
                .creatorProfileUrl(member.getMemberProfileUrl())
                .creatorNickname(member.getNickname())
                .selledIconCount(selledIconCount)
                .settlement(settlement)
                .iconGroupOrderedResponses(iconGroupOrderedResponses)
                .build();
    }
}