package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupResponses(
        long iconGroupId,

        String name,

        List<IconResponse> iconResponses
) {
    public static IconGroupResponses fromEntity(IconGroup iconGroup, List<IconResponse> iconResponses) {
        return IconGroupResponses.builder()
                .iconGroupId(iconGroup.getId())
                .name(iconGroup.getName())
                .iconResponses(iconResponses)
                .build();
    }
}
