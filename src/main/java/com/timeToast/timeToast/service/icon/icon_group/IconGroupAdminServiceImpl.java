package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponse;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.service.icon.icon.IconService;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupAdminServiceImpl implements IconGroupAdminService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final PaymentRepository paymentRepository;
    private final IconService iconService;
    private final FileUploadService fileUploadService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public Response postIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, long memberId) {

        IconGroup iconGroup = iconGroupRepository.save(iconGroupPostRequest.toEntity(iconGroupPostRequest, memberId,IconState.WAITING));
        iconService.postIconSet(files, iconGroup.getId());

        String iconGroupUrl = baseUrl +  ICON_GROUP.value() + SLASH.value() + IMAGE.value() + SLASH.value() + iconGroup.getId();
        String thumbnailImageUrl = fileUploadService.uploadfile(thumbnailIcon, iconGroupUrl);
        iconGroup.updateThumbnailImageUrl(thumbnailImageUrl);

        log.info("save icon group");
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional
    public Response posttestIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, long memberId) {

        IconGroup iconGroup = iconGroupRepository.save(iconGroupPostRequest.toEntity(iconGroupPostRequest, memberId,IconState.WAITING));
        iconService.postIconSet(files, iconGroup.getId());

        String iconGroupUrl = ICON.value() + SLASH.value() + IMAGE.value() + SLASH.value() + iconGroup.getId();
        String thumbnailImageUrl = fileUploadService.uploadfile(thumbnailIcon, iconGroupUrl);
        iconGroup.updateThumbnailImageUrl(thumbnailImageUrl);

        log.info("save icon group");
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Transactional(readOnly = true)
    @Override
    public IconGroupCreatorResponses getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponses = new ArrayList<>();

        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);

        iconGroups.forEach(
                iconGroup ->
                        iconGroupCreatorResponses.add(IconGroupCreatorResponse.fromEntity(iconGroup)));

        return new IconGroupCreatorResponses(iconGroupCreatorResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupCreatorDetailResponse getIconGroupDetailForCreator(final long memberId, final long iconGroupId) {
        Optional<IconGroup> iconGroup = iconGroupRepository.getByIdAndMemberId(iconGroupId, memberId);

        if (iconGroup.isPresent()) {
            List<Icon> icons = iconRepository.findAllByIconGroupId(iconGroupId);
            List<String> iconImageUrls = new ArrayList<>();
            icons.forEach(iconImage -> iconImageUrls.add(iconImage.getIconImageUrl()));

            Member member = memberRepository.getById(memberId);

            List<Payment> payments = paymentRepository.findAllByItemId(iconGroupId);
            long income = payments.stream()
                    .mapToLong(Payment::getAmount)
                    .sum();

            IconGroupOrderedResponse iconGroupOrderedResponse = IconGroupOrderedResponse.of(iconGroup.get(), iconImageUrls, payments.size(), income);
            return IconGroupCreatorDetailResponse.fromEntity(iconGroupOrderedResponse, iconGroup.get(), member);
        } else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }

    @Transactional
    @Override
    public IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest){
        System.out.println(iconGroupStateRequest.toString());
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupStateRequest.iconGroupId());
        iconGroup.updateIconState(iconGroupStateRequest.iconState());
        return IconGroupInfoResponse.from(iconGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getIconGroupForNonApproval() {
        List<IconGroupInfoResponse> iconGroupNonApprovalResponses =
                iconGroupRepository.findAllByIconState(IconState.WAITING).stream().map(
                        iconGroup -> IconGroupInfoResponse.from(iconGroup)
                ).toList();

        return new IconGroupInfoResponses(iconGroupNonApprovalResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponse getIconGroupDetail(final long iconGroupId){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(IconResponse::from).toList();

        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .title(iconGroup.getName())
                .creatorNickname(creator.getNickname())
                .price(iconGroup.getPrice())
                .iconState(iconGroup.getIconState())
                .description(iconGroup.getDescription())
                .iconResponses(iconResponses)
                .build();

    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getAllIconGroups(){
        List<IconGroupInfoResponse> iconGroupInfoResponses = iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN).stream().map(
                IconGroupInfoResponse::from
        ).toList();
        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }


    @Transactional(readOnly = true)
    @Override
    public CreatorIconInfos getIconGroupsByCreator(long creatorId) {
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(creatorId);
        List<CreatorIconInfo> creatorIconInfos = new ArrayList<>();
        iconGroups.forEach(
                iconGroup ->
                {
                    int salesIconCount = paymentRepository.findAllByItemIdAndItemType(iconGroup.getId(), ItemType.ICON).size();
                    creatorIconInfos.add(
                            CreatorIconInfo.builder()
                                    .title(iconGroup.getName())
                                    .revenue(salesIconCount * iconGroup.getPrice())
                                    .salesCount(salesIconCount)
                                    .iconImageUrl(iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(Icon::getIconImageUrl).toList())
                                    .build()
                    );
                }
        );

        return CreatorIconInfos.builder()
                .salesIconCount(creatorIconInfos.stream().mapToInt(CreatorIconInfo::salesCount).sum())
                .totalRevenue(creatorIconInfos.stream().mapToInt(CreatorIconInfo::revenue).sum())
                .createdIconCount(iconGroupRepository.findAllByMemberId(creatorId).size())
                .creatorIconInfos(creatorIconInfos)
                .build();
    }


}