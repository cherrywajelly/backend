package com.timeToast.timeToast.repository.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IconMemberJpaRepository extends JpaRepository<IconMember, Long> {
    IconMember getByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
    Optional<IconMember> findByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
    List<IconMember> findByMemberId(final long memberId);
    void deleteAllByMemberId(final long memberId);
}
