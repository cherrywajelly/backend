package com.timeToast.timeToast.dto.payment.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;

public record IconGroupSummary(
        String title,
        IconType iconType,
        long count
) {

}
