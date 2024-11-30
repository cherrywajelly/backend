package com.timeToast.timeToast.dto.creator.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfos(
        int salesIconCount,
        int revenue,
        List<CreatorIconInfo> creatorIconInfos
) {
}
