package com.timeToast.timeToast.repository.member_icon;

import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_MEMBER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MemberIconRepositoryImpl implements MemberIconRepository{
    private final MemberIconJpaRepository memberIconJpaRepository;

    @Override
    public IconMember getById(final long memberIconId) { return memberIconJpaRepository.findById(memberIconId).orElseThrow(() -> new NotFoundException(ICON_MEMBER_NOT_FOUND.getMessage())); }

    @Override
    public Optional<IconMember> findByMemberAndIconGroup(Member member, IconGroup iconGroup) {
        return memberIconJpaRepository.findByMemberAndIconGroup(member, iconGroup);
    }

    @Override
    public IconMember save(final IconMember memberIcon) {
        return memberIconJpaRepository.save(memberIcon);
    }
}