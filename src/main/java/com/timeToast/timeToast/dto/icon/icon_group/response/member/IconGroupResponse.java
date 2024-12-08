package com.timeToast.timeToast.dto.icon.icon_group.response.member;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupResponse(
        long iconGroupId,

        String name,

        List<IconResponse> icon
) {
    public static IconGroupResponse fromEntity(IconGroup iconGroup, List<IconResponse> iconResponses) {
        return IconGroupResponse.builder()
                .iconGroupId(iconGroup.getId())
                .name(iconGroup.getName())
                .icon(iconResponses)
                .build();
    }
}
