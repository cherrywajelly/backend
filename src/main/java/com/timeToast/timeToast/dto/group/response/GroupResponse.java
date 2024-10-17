package com.timeToast.timeToast.dto.group.response;

import com.timeToast.timeToast.domain.group.Group;
import lombok.Builder;

@Builder
public record GroupResponse(
        long groupId,
        String groupName,
        String groupProfileUrl
) {

    public static GroupResponse from(final Group group){
        return GroupResponse.builder()
                .groupId(group.getId())
                .groupName(group.getName())
                .groupProfileUrl(group.getGroupProfileUrl())
                .build();
    }
}
