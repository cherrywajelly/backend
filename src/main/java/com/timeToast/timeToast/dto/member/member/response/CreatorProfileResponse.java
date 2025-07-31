package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOrderedResponses;
import lombok.Builder;

@Builder
public record CreatorProfileResponse (
        CreatorInfoResponse creatorInfoResponse,
        IconGroupOrderedResponses iconGroupOrderedResponses,
        long createdIconCount,
        long soldIconCount,
        long revenue,
        long settlement
) {
    public static CreatorProfileResponse from(CreatorInfoResponse creatorInfoResponse, IconGroupOrderedResponses iconGroupOrderedResponses, final int createdIconCount, final int soldIconCount, final long revenue, final long settlement) {
        return CreatorProfileResponse.builder()
                .creatorInfoResponse(creatorInfoResponse)
                .iconGroupOrderedResponses(iconGroupOrderedResponses)
                .createdIconCount(createdIconCount)
                .soldIconCount(soldIconCount)
                .revenue(revenue)
                .settlement(settlement)
                .build();
    }
}
