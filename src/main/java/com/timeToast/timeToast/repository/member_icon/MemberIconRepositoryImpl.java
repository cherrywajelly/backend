package com.timeToast.timeToast.repository.member_icon;

import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBERICON_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class MemberIconRepositoryImpl implements MemberIconRepository{
    private final MemberIconJpaRepository memberIconJpaRepository;

    @Override
    public MemberIcon getById(final long memberIconId) { return memberIconJpaRepository.findById(memberIconId).orElseThrow(() -> new NotFoundException(MEMBERICON_NOT_FOUND.getMessage())); }

    @Override
    public Optional<MemberIcon> findByMemberAndIconGroup(final long memberId, final long iconGroupId) {
        return memberIconJpaRepository.findByMemberAndIconGroup(memberId, iconGroupId);
    }

    @Override
    public MemberIcon save(final MemberIcon memberIcon) {
        return memberIconJpaRepository.save(memberIcon);
    }
}
