package com.timeToast.timeToast.repository.team.team_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;

import java.util.List;

public interface TeamMemberRepository {

    TeamMember save(final TeamMember teamMember);
    List<TeamMember> findAllByMemberId(final long memberId);
    List<TeamMember> findAllByTeamId(final long teamId);
    void delete(final TeamMember teamMember);
}
