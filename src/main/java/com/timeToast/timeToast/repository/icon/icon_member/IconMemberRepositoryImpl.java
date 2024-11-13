package com.timeToast.timeToast.repository.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_MEMBER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class IconMemberRepositoryImpl implements IconMemberRepository {
    private final IconMemberJpaRepository iconMemberJpaRepository;

    @Override
    public IconMember getById(final long memberIconId) { return iconMemberJpaRepository.findById(memberIconId).orElseThrow(() -> new NotFoundException(ICON_MEMBER_NOT_FOUND.getMessage())); }

    @Override
    public IconMember getByMemberIdAndIconGroupId(final long memberId, final long iconGroupId) {
        return iconMemberJpaRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId);
    }

    @Override
    public IconMember save(final IconMember memberIcon) {
        return iconMemberJpaRepository.save(memberIcon);
    }

    @Override
    public void deleteById(final long memberIconId) {
        iconMemberJpaRepository.deleteById(memberIconId);
    }

    @Override
    public void deleteAllByMemberId(final long memberId){
        iconMemberJpaRepository.deleteAllByMemberId(memberId);
    }
}