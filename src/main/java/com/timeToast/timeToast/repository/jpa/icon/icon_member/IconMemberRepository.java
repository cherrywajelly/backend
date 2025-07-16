package com.timeToast.timeToast.repository.jpa.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;

import java.util.List;
import java.util.Optional;

public interface IconMemberRepository {
    IconMember save(final IconMember memberIcon);
    IconMember getById(final long memberIconId);
    Optional<IconMember> findByMemberIdAndIconGroupId(long memberId, long iconGroupId);
    List<IconMember> findByMemberId(final long memberId);
    void deleteById(final long memberIconId);
    void deleteAllByMemberId(final long memberId);
}
