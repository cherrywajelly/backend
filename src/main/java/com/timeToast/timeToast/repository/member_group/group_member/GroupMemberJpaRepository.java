package com.timeToast.timeToast.repository.member_group.group_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberJpaRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findAllByMemberId(final long memberId);
    List<TeamMember> findAllByMemberGroupId(final long memberGroupId);
}
