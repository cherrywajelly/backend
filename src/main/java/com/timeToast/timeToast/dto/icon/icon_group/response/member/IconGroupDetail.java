package com.timeToast.timeToast.dto.icon.icon_group.response.member;

import com.timeToast.timeToast.dto.icon.icon.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupDetail(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        boolean isBuy,
        List<IconResponse> icons
) {
}
