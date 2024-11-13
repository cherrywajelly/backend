package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;

import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.icon_member.MemberIconRepository;
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
    private final MemberIconRepository memberIconRepository;
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;

    @Transactional
    @Override
    public void buyIconGroup(long memberId, long iconGroupId){
        Member member = memberRepository.getById(memberId);
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        if (member != null && iconGroup != null) {
            // 중복 구매 방지
            if(memberIconRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId).isEmpty()) {
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
        List<IconGroup> iconGroups = iconGroupRepository.findByMemberId(memberId);
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        iconGroups.forEach(iconGroup -> {
            List<IconResponse> iconResponses = new ArrayList<>();
            List<Icon> icons = iconRepository.findAllByIconGroupId(iconGroup.getId());

            icons.forEach(icon -> {
                iconResponses.add(new IconResponse(icon.getId(), icon.getIconImageUrl()));
            });

            iconGroupResponses.add(new IconGroupResponses(iconGroup.getId(), iconGroup.getName(), iconResponses));
        });

        return iconGroupResponses;
    }

    @Transactional
    @Override
    public void deleteIconGroup(final long memberId, final long iconGroupId){
    }
}
