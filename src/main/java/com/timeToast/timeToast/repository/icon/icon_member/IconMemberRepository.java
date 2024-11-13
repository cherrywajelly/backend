package com.timeToast.timeToast.repository.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;

import java.util.List;
import java.util.Optional;

public interface IconMemberRepository {
    IconMember getById(final long memberIconId);
    IconMember getByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
    Optional<IconMember> findByMemberIdAndIconGroupId(long memberId, long iconGroupId);
    List<IconMember> findByMemberId(final long memberId);
    IconMember save(final IconMember memberIcon);
    void deleteById(final long memberIconId);
    void deleteAllByMemberId(final long memberId);
}
