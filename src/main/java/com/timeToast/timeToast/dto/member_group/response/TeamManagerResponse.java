package com.timeToast.timeToast.dto.member_group.response;

import com.timeToast.timeToast.domain.team.team.Team;
import lombok.Builder;

@Builder
public record TeamManagerResponse(
        long teamId,
        String teamProfileUrl,
        String name
) {
    public static TeamManagerResponse from(Team team) {
        return TeamManagerResponse.builder()
                .teamId(team.getId())
                .teamProfileUrl(team.getTeamProfileUrl())
                .name(team.getName())
                .build();
    }
}
