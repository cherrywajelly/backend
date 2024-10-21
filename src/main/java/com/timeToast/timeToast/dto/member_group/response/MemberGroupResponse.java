package com.timeToast.timeToast.dto.member_group.response;

import com.timeToast.timeToast.domain.member_group.MemberGroup;
import lombok.Builder;

@Builder
public record MemberGroupResponse(
        long groupId,
        String groupName,
        String groupProfileUrl
) {

    public static MemberGroupResponse from(final MemberGroup memberGroup){
        return MemberGroupResponse.builder()
                .groupId(memberGroup.getId())
                .groupName(memberGroup.getName())
                .groupProfileUrl(memberGroup.getGroupProfileUrl())
                .build();
    }
}
