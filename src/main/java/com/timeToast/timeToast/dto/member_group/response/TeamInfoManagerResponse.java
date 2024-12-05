package com.timeToast.timeToast.dto.member_group.response;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.dto.member.member.response.ManagerProfileResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record TeamInfoManagerResponse (
        long teamId,
        String teamProfileUrl,
        String name,
        LocalDate createdAt,
        List<ManagerProfileResponse> managerProfileResponses
) {
    public static TeamInfoManagerResponse from(final Team team, List<ManagerProfileResponse> managerProfileResponses) {
        return TeamInfoManagerResponse.builder()
                .teamId(team.getId())
                .teamProfileUrl(team.getTeamProfileUrl())
                .name(team.getName())
                .createdAt(team.getCreatedAt().toLocalDate())
                .managerProfileResponses(managerProfileResponses)
                .build();
    }
}