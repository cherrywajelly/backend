package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.group.member_group.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupJpaRepository extends JpaRepository<MemberGroup, Long> {

    List<MemberGroup> findAllByMemberId(final long memberId);

    List<MemberGroup> findAllByGroupId(final long groupId);
}
