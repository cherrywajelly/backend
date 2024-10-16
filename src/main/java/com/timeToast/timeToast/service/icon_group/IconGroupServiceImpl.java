package com.timeToast.timeToast.service.icon_group;

import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import com.timeToast.timeToast.repository.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.repository.member_icon.MemberIconRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class IconGroupServiceImpl implements IconGroupService{
    private final MemberIconRepository memberIconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;

    public void buyIconGroup(long memberId, long iconGroupId){
        Member member = memberRepository.getById(memberId);
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        // 중복 구매 방지
        if(memberIconRepository.findByMemberAndIconGroup(member, iconGroup).isEmpty()) {
            memberIconRepository.save(MemberIcon.builder()
                    .member(member)
                    .iconGroup(iconGroup)
                    .build());

            System.out.println("아이콘 그룹을 구입하였습니다");
        }else {
            // TODO 예외처리
            System.out.println("아이콘 그룹 구입 못함");
        }
    }


}
