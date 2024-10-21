package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;

import java.util.Optional;

public interface MemberGroupRepository {
    MemberGroup save(final MemberGroup memberGroup);
    MemberGroup getById(final long memberGroupId);
    Optional<MemberGroup> findById(final long memberGroupId);
    void deleteByMemberGroupId(final long memberGroupId);
}
