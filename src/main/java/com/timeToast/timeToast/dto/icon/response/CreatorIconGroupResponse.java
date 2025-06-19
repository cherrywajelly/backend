package com.timeToast.timeToast.dto.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconGroupResponse(
        List<IconGroupOverview> iconGroupOverviews,
        long totalIconCount,
        long totalOrderCount,
        long totalIncome,
        long totalSettlement
) {

}
