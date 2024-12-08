package com.timeToast.timeToast.dto.team.response;

import com.timeToast.timeToast.domain.team.team.Team;
import lombok.Builder;

@Builder
public record TeamDataManagerResponse (
        String teamProfileUrl,
        String teamName
) {
    public static TeamDataManagerResponse from(final Team team) {
        return TeamDataManagerResponse.builder()
                .teamProfileUrl(team.getTeamProfileUrl())
                .teamName(team.getName())
                .build();
    }
}
