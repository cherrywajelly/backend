package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;

import java.util.List;

public interface MemberGroupRepository {

    MemberGroup save(final MemberGroup memberGroup);

    List<MemberGroup> findAllByMemberId(final long memberId);
    List<MemberGroup> findAllByGroupId(final long groupId);

    void delete(final MemberGroup memberGroup);
}
