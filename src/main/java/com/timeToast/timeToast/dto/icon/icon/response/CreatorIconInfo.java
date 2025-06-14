package com.timeToast.timeToast.dto.icon.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfo(
        String title,
        int revenue,
        int salesCount,
        List<String> iconImageUrl
) {
}
