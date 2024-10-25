package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.member_icon.MemberIconRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.icon_member.MemberIconRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICONGROUP_ALREADY_EXISTS;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupServiceImpl implements IconGroupService{
    private final MemberIconRepository memberIconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void buyIconGroup(long memberId, long iconGroupId){
        Member member = memberRepository.getById(memberId);
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        // 중복 구매 방지
        if(memberIconRepository.findByMemberAndIconGroup(member, iconGroup).isEmpty() && member != null) {
            memberIconRepository.save(IconMember.builder()
                    .memberId(memberId)
                    .iconGroupId(iconGroupId)
                    .build());

            log.info("buy new icon group");
        }else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }


}
