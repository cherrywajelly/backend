package com.timeToast.timeToast.dto.icon.icon_group.request;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;

public record IconGroupStateRequest(
        long iconGroupId,
        IconState iconState
) {
}
