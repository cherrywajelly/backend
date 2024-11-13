package com.timeToast.timeToast.repository.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IconMemberJpaRepository extends JpaRepository<IconMember, Long> {
    IconMember findByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
    void deleteAllByMemberId(final long memberId);
}
