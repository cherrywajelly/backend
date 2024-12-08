package com.timeToast.timeToast.dto.icon.icon_group.response.admin;

import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupManagerResponse (
        String iconGroupName,
        List<String> iconImages
) {
    public static IconGroupManagerResponse from(String iconGroupName, List<String> iconImages) {
        return IconGroupManagerResponse.builder()
                .iconGroupName(iconGroupName)
                .iconImages(iconImages)
                .build();
    }
}
