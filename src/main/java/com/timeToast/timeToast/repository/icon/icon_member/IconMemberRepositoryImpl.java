package com.timeToast.timeToast.repository.icon.icon_member;

import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_MEMBER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class IconMemberRepositoryImpl implements IconMemberRepository {
    private final IconMemberJpaRepository memberIconJpaRepository;

    @Override
    public IconMember getById(final long memberIconId) { return memberIconJpaRepository.findById(memberIconId).orElseThrow(() -> new NotFoundException(ICON_MEMBER_NOT_FOUND.getMessage())); }

    @Override
    public IconMember getByMemberIdAndIconGroupId(final long memberId, final long iconGroupId) {
        return memberIconJpaRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId);
    }

    public List<IconMember> findByMemberId(final long memberId){
        return memberIconJpaRepository.findByMemberId(memberId);
    }

    @Override
    public IconMember save(final IconMember memberIcon) {
        return memberIconJpaRepository.save(memberIcon);
    }


    @Override
    public void deleteById(final long memberIconId) {
        memberIconJpaRepository.deleteById(memberIconId);
    }
}