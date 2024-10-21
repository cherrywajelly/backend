package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberGroupJpaRepository extends JpaRepository<MemberGroup, Long> {

    MemberGroup getMemberGroupById(final long memberGroupId);
}
