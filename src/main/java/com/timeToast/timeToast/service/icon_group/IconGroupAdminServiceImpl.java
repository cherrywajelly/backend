package com.timeToast.timeToast.service.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.repository.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class IconGroupAdminServiceImpl implements IconGroupAdminService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;

    public void postIconGroup(IconGroupPostRequest iconGroupPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        IconGroup iconGroup = iconGroupPostRequest.toEntity(iconGroupPostRequest, member);
        iconGroup.updateIconState(IconState.UNREGISTERED);
        iconGroupRepository.save(iconGroup);
        System.out.println("아이콘 그룹 등록 완료");
    }

}
