package com.timeToast.timeToast.dto.icon.response;

import lombok.Builder;

@Builder
public record UserIconGroupDetail(
        IconGroupDetail iconGroupDetail,
        boolean isBuy
) {
}
