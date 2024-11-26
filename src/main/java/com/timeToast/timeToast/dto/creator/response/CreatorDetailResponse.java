package com.timeToast.timeToast.dto.creator.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorDetailResponse(

        String profileUrl,
        String nickname,
        int iconTotalCount,
        int salesIconTotalCount,
        int totalRevenue,
        String accountNumber,
        List<CreatorIconInfo> creatorIconInfos
) {
}
