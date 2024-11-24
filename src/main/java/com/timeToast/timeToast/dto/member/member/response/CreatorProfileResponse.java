package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import lombok.Builder;

@Builder
public record CreatorProfileResponse (
        CreatorInfoResponse creatorInfoResponse,
        IconGroupOrderedResponses iconGroupOrderedResponses,
        long createdIconCount,
        long selledIconCount,
        long settlement
) {
    public static CreatorProfileResponse from(CreatorInfoResponse creatorInfoResponse, IconGroupOrderedResponses iconGroupOrderedResponses, final int createdIconCount, final int selledIconCount, final long settlement) {
        return CreatorProfileResponse.builder()
                .creatorInfoResponse(creatorInfoResponse)
                .iconGroupOrderedResponses(iconGroupOrderedResponses)
                .createdIconCount(createdIconCount)
                .selledIconCount(selledIconCount)
                .settlement(settlement)
                .build();
    }
}
