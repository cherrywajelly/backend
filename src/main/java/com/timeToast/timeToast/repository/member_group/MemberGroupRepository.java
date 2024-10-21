package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;

import java.util.Optional;

public interface MemberGroupRepository {
    MemberGroup save(final MemberGroup memberGroup);
    MemberGroup getById(final long groupId);
    Optional<MemberGroup> findById(final long groupId);
    void deleteByGroupId(final long groupId);
}
