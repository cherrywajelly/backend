package com.timeToast.timeToast.dto.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupOverview(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        List<IconResponse> icons,
        IconGroupOrderInfo iconGroupOrderInfo

) {
}
