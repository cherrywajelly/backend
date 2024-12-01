package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;

import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupServiceImpl implements IconGroupService{
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final IconMemberRepository iconMemberRepository;


    @Transactional(readOnly = true)
    @Override
    public IconGroupResponses getToastIconGroups(final long memberId){
        return getIconGroups(memberId,IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupResponses getJamIconGroups(long memberId) {
        return getIconGroups(memberId,IconType.JAM);
    }

    private IconGroupResponses getIconGroups(final long memberId, final IconType iconType){
        List<IconGroupResponse> iconGroupResponses = new ArrayList<>();

        iconMemberRepository.findByMemberId(memberId).forEach(iconMember -> {
            IconGroup iconGroup = iconGroupRepository.getById(iconMember.getIconGroupId());

            if(iconGroup.getIconType().equals(iconType)){
                List<IconResponse> iconResponses = new ArrayList<>();
                iconRepository.findAllByIconGroupId(iconMember.getIconGroupId())
                        .forEach(icon -> iconResponses.add(new IconResponse(icon.getId(), icon.getIconImageUrl())));

                iconGroupResponses.add(new IconGroupResponse(iconGroup.getId(), iconGroup.getName(), iconResponses));
            }

        });
        return new IconGroupResponses(iconGroupResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupMarketResponses getAllToastsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupMarketResponses getAllJamsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.JAM);
    }



    @Transactional(readOnly = true)
    @Override
    public IconGroupMarketDetailResponse getIconGroupDetail(final long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(IconResponse::from).toList();
        String thumbnailImageUrl = null;
        if(iconResponses.stream().findFirst().isPresent()){
            thumbnailImageUrl = iconResponses.stream().findFirst().get().iconImageUrl();
        }
        return IconGroupMarketDetailResponse.builder()
                .thumbnailImageUrl(thumbnailImageUrl)
                .title(iconGroup.getName())
                .creatorNickname(creator.getNickname())
                .price(iconGroup.getPrice())
                .iconState(iconGroup.getIconState())
                .iconResponses(iconResponses)
                .isBuy(iconMemberRepository.findByMemberIdAndIconGroupId(iconGroup.getId(), iconGroup.getId()).isPresent())
                .build();
    }


    @Transactional
    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId){
        IconMember iconMember = iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId);

        if (iconMember == null) {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }

        iconMemberRepository.deleteById(iconMember.getId());
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    private IconGroupMarketResponses getAllIconGroups(final long memberId, final IconType iconType){
        List<IconGroupMarketResponse> iconGroupMarketResponses = new ArrayList<>();
        iconGroupRepository.findAllByIconTypeAndIconBuiltin(iconType, IconBuiltin.NONBUILTIN, IconState.REGISTERED).forEach(
                iconGroup ->{
                    Optional<Icon> icon =  iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().findFirst();
                    if(icon.isPresent()){
                        iconGroupMarketResponses.add(
                                        IconGroupMarketResponse.builder()
                                                .iconGroupId(iconGroup.getId())
                                                .title(iconGroup.getName())
                                                .thumbnailImageUrl(icon.get().getIconImageUrl())
                                .creatorNickname(memberRepository.getById(iconGroup.getMemberId()).getNickname())
                                .iconType(iconGroup.getIconType())
                                .isBuy(iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroup.getId()).isPresent())
                                .build());
                    }

                }

        );

        return new IconGroupMarketResponses(iconGroupMarketResponses);
    }
}
