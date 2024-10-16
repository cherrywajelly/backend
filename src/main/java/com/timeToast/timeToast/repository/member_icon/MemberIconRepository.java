package com.timeToast.timeToast.repository.member_icon;

import com.timeToast.timeToast.domain.member_icon.MemberIcon;

import java.util.Optional;

public interface MemberIconRepository {
    MemberIcon getById(final long memberIconId);
    Optional<MemberIcon> findByMemberAndIconGroup(final long memberId, final long iconGroupId);
    MemberIcon save(final MemberIcon memberIcon);
}
