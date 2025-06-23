package com.timeToast.timeToast.dto.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconGroup(
        IconGroupInfo iconGroupInfo,
        List<IconResponse> icons,
        IconGroupOrderInfo iconGroupOrderInfo

) {
}
