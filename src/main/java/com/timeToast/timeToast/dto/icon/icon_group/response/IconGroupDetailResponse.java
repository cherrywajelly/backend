package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.util.*;

@Builder
public record IconGroupDetailResponse(
        String thumbnailImageUrl,
        String title,
        String creatorNickname,
        IconState iconState,
        int price,
        List<IconResponse> iconResponses

) {


}
