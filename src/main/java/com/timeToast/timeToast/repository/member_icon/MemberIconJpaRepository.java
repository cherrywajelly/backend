package com.timeToast.timeToast.repository.member_icon;

import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberIconJpaRepository extends JpaRepository<MemberIcon, Long> {
    Optional<MemberIcon> findByMemberAndIconGroup(final long memberId, final long iconGroupId);
}
