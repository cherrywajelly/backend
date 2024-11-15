package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupAdminServiceImpl implements IconGroupAdminService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;
    private final IconRepository iconRepository;

    @Transactional
    public void postIconGroup(IconGroupPostRequest iconGroupPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        if(member == null) {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        } else {
            IconGroup iconGroup = iconGroupPostRequest.toEntity(iconGroupPostRequest, memberId);
            iconGroup.updateIconState(IconState.UNREGISTERED);
            iconGroupRepository.save(iconGroup);
            log.info("save icon group");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public List<IconGroupCreatorResponse> getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponses = new ArrayList<>();
        Member member = memberRepository.getById(memberId);

        if(member == null) {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        } else {
            List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);
            iconGroups.forEach(
                    iconGroup -> {
                        List<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId());
                        iconGroupCreatorResponses.add(new IconGroupCreatorResponse(iconGroup.getId(), icon.get(0).getIconImageUrl(), iconGroup.getName()));
                    }
            );
        }
        return iconGroupCreatorResponses;
    }
}
