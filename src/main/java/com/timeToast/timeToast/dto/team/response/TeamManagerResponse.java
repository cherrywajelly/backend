package com.timeToast.timeToast.dto.team.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.team.team.Team;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TeamManagerResponse(
        long teamId,
        String teamProfileUrl,
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate createdAt,
        int memberCount
) {
    public static TeamManagerResponse from(final Team team, final int memberCount) {
        return TeamManagerResponse.builder()
                .teamId(team.getId())
                .teamProfileUrl(team.getTeamProfileUrl())
                .name(team.getName())
                .createdAt(team.getCreatedAt().toLocalDate())
                .memberCount(memberCount)
                .build();
    }
}
