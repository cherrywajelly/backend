package com.timeToast.timeToast.repository.team.team_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberJpaRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findAllByMemberId(final long memberId);
    List<TeamMember> findAllByTeamId(final long teamId);
}
