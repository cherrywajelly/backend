package com.timeToast.timeToast.dto.member_group.response;

import com.timeToast.timeToast.domain.team.team.Team;
import lombok.Builder;

@Builder
public record MemberGroupResponse(
        long groupId,
        String groupName,
        String groupProfileUrl
) {

    public static MemberGroupResponse from(final Team memberGroup){
        return MemberGroupResponse.builder()
                .groupId(memberGroup.getId())
                .groupName(memberGroup.getName())
                .groupProfileUrl(memberGroup.getTeamProfileUrl())
                .build();
    }
}
