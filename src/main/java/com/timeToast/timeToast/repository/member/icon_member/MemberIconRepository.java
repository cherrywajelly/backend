package com.timeToast.timeToast.repository.member.icon_member;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;

import java.util.Optional;

public interface MemberIconRepository {
    IconMember getById(final long memberIconId);
    Optional<IconMember> findByMemberIdAndIconGroupId(final long memberId, final long iconGroupId);
    IconMember save(final IconMember memberIcon);
}
