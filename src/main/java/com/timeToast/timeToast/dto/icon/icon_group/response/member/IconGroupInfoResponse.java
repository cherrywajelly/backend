package com.timeToast.timeToast.dto.icon.icon_group.response.member;

import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;

public record IconGroupInfoResponse(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        boolean isBuy
) {
}
