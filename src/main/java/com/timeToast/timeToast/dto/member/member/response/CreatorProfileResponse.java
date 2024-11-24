package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import lombok.Builder;

@Builder
public record CreatorProfileResponse (
        IconGroupOrderedResponses iconGroupOrderedResponses,
        CreatorInfoResponse creatorInfoResponse,
        int createdIconCount,
        int selledIconCount,
        long settlement
) {
    public static CreatorProfileResponse from(IconGroupOrderedResponses iconGroupOrderedResponses, CreatorInfoResponse creatorInfoResponse, final int createdIconCount, final int selledIconCount, final long settlement) {
        return CreatorProfileResponse.builder()
                .iconGroupOrderedResponses(iconGroupOrderedResponses)
                .creatorInfoResponse(creatorInfoResponse)
                .createdIconCount(createdIconCount)
                .selledIconCount(selledIconCount)
                .settlement(settlement)
                .build();
    }
}
