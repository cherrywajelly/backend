package com.timeToast.timeToast.dto.icon.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfos(
        long salesIconCount,
        long totalIncome,
        int totalIconCount,
        List<CreatorIconInfo> creatorIconInfos
) {
}
