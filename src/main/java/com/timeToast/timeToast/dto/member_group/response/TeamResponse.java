package com.timeToast.timeToast.dto.member_group.response;

import com.timeToast.timeToast.domain.team.team.Team;
import lombok.Builder;

@Builder
public record TeamResponse(
        long teamId,
        String teamName,
        String teamProfileUrl
) {

    public static TeamResponse from(final Team team){
        return TeamResponse.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .teamProfileUrl(team.getTeamProfileUrl())
                .build();
    }
}
