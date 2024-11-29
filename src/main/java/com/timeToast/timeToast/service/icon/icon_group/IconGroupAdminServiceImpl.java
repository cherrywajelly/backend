package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupAdminServiceImpl implements IconGroupAdminService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;
    private final IconRepository iconRepository;

    @Transactional
    public Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        if(member == null) {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        } else {
            IconGroup iconGroup = iconGroupPostRequest.toEntity(iconGroupPostRequest, memberId);
            iconGroup.updateIconState(IconState.WAITING);
            iconGroupRepository.save(iconGroup);
            log.info("save icon group");
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Transactional(readOnly = true)
    @Override
    public IconGroupCreatorResponses getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponses = new ArrayList<>();
        Member member = memberRepository.getById(memberId);

        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(member.getId());
        iconGroups.forEach(
                iconGroup -> {
                    List<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId());
                    iconGroupCreatorResponses.add(new IconGroupCreatorResponse(iconGroup.getId(), icon.get(0).getIconImageUrl(), iconGroup.getName()));
                });

        return new IconGroupCreatorResponses(iconGroupCreatorResponses);
    }

    @Transactional
    @Override
    public IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest){
        System.out.println(iconGroupStateRequest.toString());
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupStateRequest.iconGroupId());
        iconGroup.updateIconState(iconGroupStateRequest.iconState());
        String thumbnailImageUrl = null;
        Optional<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().findFirst();
        if(icon.isPresent()){
            thumbnailImageUrl = icon.get().getIconImageUrl();
        }
        return IconGroupInfoResponse.from(iconGroup,thumbnailImageUrl);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getIconGroupForNonApproval() {
        List<IconGroupInfoResponse> iconGroupNonApprovalResponses =
                iconGroupRepository.findAllByIconState(IconState.WAITING).stream().map(
                        iconGroup -> {
                            String thumbnail = null;
                            Optional<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().findFirst();
                            if(icon.isPresent()){
                                thumbnail = icon.get().getIconImageUrl();
                            }
                            return IconGroupInfoResponse.from(iconGroup, thumbnail);
                        }
                ).toList();

        return new IconGroupInfoResponses(iconGroupNonApprovalResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponse getIconGroupDetail(final long iconGroupId){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(IconResponse::from).toList();
        String thumbnailImageUrl = null;
        if(iconResponses.stream().findFirst().isPresent()){
            thumbnailImageUrl = iconResponses.stream().findFirst().get().iconImageUrl();
        }
        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl(thumbnailImageUrl)
                .title(iconGroup.getName())
                .creatorNickname(creator.getNickname())
                .price(iconGroup.getPrice())
                .iconState(iconGroup.getIconState())
                .iconResponses(iconResponses)
                .build();

    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getAllIconGroups(){
        List<IconGroupInfoResponse> iconGroupInfoResponses = iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN).stream().map(
                iconGroup -> {
                    return IconGroupInfoResponse.from( iconGroup,
                            iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().findFirst().get().getIconImageUrl());
                }
        ).toList();
        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }




}
