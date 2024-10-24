package com.timeToast.timeToast.repository.member_group.member_group;

import com.timeToast.timeToast.domain.team.team.Team;

import java.util.Optional;

public interface MemberGroupRepository {
    Team save(final Team memberGroup);
    Team getById(final long memberGroupId);
    Optional<Team> findById(final long memberGroupId);
    void deleteByMemberGroupId(final long memberGroupId);
}
