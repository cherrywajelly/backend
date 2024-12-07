package com.timeToast.timeToast.dto.icon.icon_group.response.admin;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;

public record IconGroupSummary(
        String title,
        IconType iconType,
        long count
) {

}
