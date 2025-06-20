package com.timeToast.timeToast.dto.icon.response;

import java.util.List;


public record IconGroupDetail(
        IconGroupInfo iconGroupInfo,
        List<IconResponse> icons
) {
}
