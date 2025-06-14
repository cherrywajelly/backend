package com.timeToast.timeToast.dto.member.member.response;

import lombok.Builder;

//TODO
@Builder
public record CreatorResponse(
        CreatorInfoResponse creatorInfo,
        long salesIconCount,
        long totalRevenue,
        int createdIconCount
) {

}
