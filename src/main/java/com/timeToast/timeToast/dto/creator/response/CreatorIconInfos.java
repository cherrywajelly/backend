package com.timeToast.timeToast.dto.creator.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfos(
        long salesIconCount,
        long totalRevenue,
        int createdIconCount,
        List<CreatorIconInfo> creatorIconInfos
) {
}
