package com.timeToast.timeToast.repository.member_icon;

import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;

import java.util.Optional;

public interface MemberIconRepository {
    MemberIcon getById(final long memberIconId);
    Optional<MemberIcon> findByMemberAndIconGroup(Member member, IconGroup iconGroup);
    MemberIcon save(final MemberIcon memberIcon);
}
