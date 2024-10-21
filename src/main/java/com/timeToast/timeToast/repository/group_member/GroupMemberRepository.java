package com.timeToast.timeToast.repository.group_member;

import com.timeToast.timeToast.domain.group_member.GroupMember;

import java.util.List;

public interface GroupMemberRepository {

    GroupMember save(final GroupMember groupMember);

    List<GroupMember> findAllByMemberId(final long memberId);
    List<GroupMember> findAllByGroupId(final long groupId);

    void delete(final GroupMember groupMember);
}
