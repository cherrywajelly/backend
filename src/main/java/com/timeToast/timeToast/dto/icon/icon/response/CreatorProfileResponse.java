package com.timeToast.timeToast.dto.icon.icon.response;

import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOrderedResponses;
import lombok.Builder;

@Builder
public record CreatorProfileResponse (
        IconGroupOrderedResponses iconGroupOrderedResponses,
        long createdIconCount,
        long soldIconCount,
        long revenue,
        long settlement
) {
    public static CreatorProfileResponse from(final IconGroupOrderedResponses iconGroupOrderedResponses,
                                              final int createdIconCount, final int soldIconCount,
                                              final long revenue, final long settlement) {
        return CreatorProfileResponse.builder()
                .iconGroupOrderedResponses(iconGroupOrderedResponses)
                .createdIconCount(createdIconCount)
                .soldIconCount(soldIconCount)
                .revenue(revenue)
                .settlement(settlement)
                .build();
    }
}
