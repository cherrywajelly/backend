package com.timeToast.timeToast.repository.member_group.group_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;

import java.util.List;

public interface GroupMemberRepository {

    TeamMember save(final TeamMember groupMember);
    List<TeamMember> findAllByMemberId(final long memberId);
    List<TeamMember> findAllByMemberGroupId(final long memberGroupId);

    void delete(final TeamMember groupMember);
}
