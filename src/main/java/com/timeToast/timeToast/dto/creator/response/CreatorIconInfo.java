package com.timeToast.timeToast.dto.creator.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfo(
        String title,
        int revenue,
        int salesIconCount,
        List<String> iconImageUrl
) {
}
