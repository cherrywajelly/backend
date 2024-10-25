package com.timeToast.timeToast.repository.member.icon_member;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberIconJpaRepository extends JpaRepository<IconMember, Long> {
    Optional<IconMember> findByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
}
