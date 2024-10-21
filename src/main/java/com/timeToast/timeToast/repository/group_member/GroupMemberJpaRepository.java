package com.timeToast.timeToast.repository.group_member;

import com.timeToast.timeToast.domain.group_member.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberJpaRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findAllByMemberId(final long memberId);
    List<GroupMember> findAllByMemberGroupId(final long memberGroupId);
}
