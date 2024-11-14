package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.util.*;

@Builder
public record IconGroupDetailResponse(
        String profileImageUrl,
        String title,
        String creatorNickname,
        int price,
        List<IconResponse> iconResponses

) {
}
