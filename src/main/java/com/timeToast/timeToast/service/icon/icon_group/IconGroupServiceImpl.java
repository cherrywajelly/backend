package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;

import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupServiceImpl implements IconGroupService{
    private final IconMemberRepository memberIconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final IconMemberRepository iconMemberRepository;

    @Transactional
    @Override
    public void buyIconGroup(final long memberId, final long iconGroupId){
        Member member = memberRepository.getById(memberId);
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        if (member != null && iconGroup != null) {
            // 중복 구매 방지
            if(memberIconRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId) == null) {
                memberIconRepository.save(IconMember.builder()
                        .memberId(memberId)
                        .iconGroupId(iconGroupId)
                        .build());

                log.info("buy new icon group");
            }else {
                throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
            }
        } else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }

    @Transactional
    @Override
    public List<IconGroupResponses> getIconGroups(long memberId){
        List<IconMember> iconMembers = iconMemberRepository.findByMemberId(memberId);
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        iconMembers.forEach(iconMember -> {
            List<IconResponse> iconResponses = new ArrayList<>();
            List<Icon> icons = iconRepository.findAllByIconGroupId(iconMember.getIconGroupId());

            icons.forEach(icon -> {
                iconResponses.add(new IconResponse(icon.getId(), icon.getIconImageUrl()));
            });

            IconGroup iconGroup = iconGroupRepository.getById(iconMember.getIconGroupId());
            iconGroupResponses.add(new IconGroupResponses(iconMember.getId(), iconGroup.getName(), iconResponses));
        });

        return iconGroupResponses;
    }

    @Transactional
    @Override
    public void deleteIconGroup(final long memberId, final long iconGroupId){
        IconMember iconMember = iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId);
        if (iconMember != null) {
            iconMemberRepository.deleteById(iconMember.getId());
        } else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }
}
