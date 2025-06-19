package com.timeToast.timeToast.dto.icon.response;

import java.util.List;


public record IconGroupDetail(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        List<IconResponse> icons
) {
}
