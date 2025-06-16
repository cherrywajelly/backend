package com.timeToast.timeToast.dto.icon.icon_group.response.member;

import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupDetail(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        boolean isBuy,
        List<IconResponse> icons
) {
}
