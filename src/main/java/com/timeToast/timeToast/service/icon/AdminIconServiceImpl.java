package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOverview;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorProfileResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminIconServiceImpl implements AdminIconService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final FileUploadService fileUploadService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public Response postIconGroup(final MultipartFile thumbnailIcon, final List<MultipartFile> files,
                                  final IconGroupPostRequest iconGroupPostRequest, final long memberId) {

        IconGroup iconGroup = iconGroupRepository.save(iconGroupPostRequest.toEntity(iconGroupPostRequest, memberId));

        String iconGroupUrl = baseUrl + ICON_GROUP.value() + SLASH.value() + iconGroup.getId() + SLASH.value() + IMAGE.value();
        String thumbnailImageUrl = fileUploadService.uploadfile(thumbnailIcon, iconGroupUrl);
        iconGroup.updateThumbnailImageUrl(thumbnailImageUrl);

        iconGroup.addIcons(postIconSet(files, iconGroup.getId()));

        log.info("save icon group ${}", iconGroup.getId());
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    private List<Icon> postIconSet(final List<MultipartFile> files, final long iconGroupId) {

        List<Icon> icons = new ArrayList<>();

        files.forEach(file-> {

            String endpoint = baseUrl + ICON_GROUP.value() + SLASH.value() + iconGroupId + SLASH.value()
                    + ICON.value() + SLASH.value() + RandomStringUtils.randomAlphanumeric(10) + SLASH.value() + IMAGE.value();

            String iconImageUrl = fileUploadService.uploadfile(file, endpoint);
            Icon icon = Icon.builder().iconImageUrl(iconImageUrl).build();
            icons.add(icon);
        });

        return icons;
    }

    @Transactional
    @Override
    public IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupStateRequest.iconGroupId());
        iconGroup.updateIconState(iconGroupStateRequest.iconState());
        return IconGroupInfoResponse.from(iconGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupOverview getIconGroupOverview(final long memberId, final long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getByIdAndMemberId(memberId,iconGroupId);
        Member member = memberRepository.getById(memberId);

        return getIconGroupOverviewByIconGroup(iconGroup,member.getNickname());
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorProfileResponse getIconGroupOverviews(final long memberId){
        Member member = memberRepository.getById(memberId);

        List<IconGroupOverview> iconGroupOverviews = iconGroupRepository.findAllByMemberId(memberId).stream()
                .map(iconGroup -> getIconGroupOverviewByIconGroup(iconGroup, member.getNickname())).toList();

        return CreatorProfileResponse.from(iconGroupOverviews);

    }

    private IconGroupOverview getIconGroupOverviewByIconGroup(final IconGroup iconGroup, final String creatorName) {

        List<Payment> payments = paymentRepository.findAllByItemId(iconGroup.getId());
        long totalRevenue = payments.stream().mapToLong(Payment::getAmount).sum();

        return IconGroupOverview.from(IconGroupSummaryInfo.from(iconGroup,creatorName), iconGroup,payments.size(),totalRevenue);
    }





    //TODO
    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getIconGroupForNonApproval() {
        List<IconGroupInfoResponse> iconGroupNonApprovalResponses =
                iconGroupRepository.findAllByIconState(IconState.WAITING)
                        .stream()
                        .map(IconGroupInfoResponse::from)
                        .toList();

        return new IconGroupInfoResponses(iconGroupNonApprovalResponses);
    }
    //TODO
    @Transactional(readOnly = true)
    @Override
    public com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupDetailResponse getIconGroupDetail(final long iconGroupId){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconGroup.getIcons().stream().map(IconResponse::from).toList();

        return com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupDetailResponse.builder()
                .thumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                .title(iconGroup.getName())
                .creatorNickname(creator.getNickname())
                .price(iconGroup.getPrice())
                .iconState(iconGroup.getIconState())
                .description(iconGroup.getDescription())
                .icons(iconResponses)
                .build();

    }
    //TODO
    @Transactional(readOnly = true)
    @Override
    public IconGroupAdminResponses getAllIconGroups(){
        List<IconGroupAdminResponse> iconGroupAdminResponses = new ArrayList<>();
        List<IconGroup> iconGroups = iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN);
        iconGroups.forEach(iconGroup -> {
            Member member = memberRepository.getById(iconGroup.getMemberId());
            if (member != null) {
                iconGroupAdminResponses.add(IconGroupAdminResponse.from(iconGroup,member.getNickname()));
            }
        });
        return new IconGroupAdminResponses(iconGroupAdminResponses);
    }

    //TODO
    @Transactional(readOnly = true)
    @Override
    public CreatorIconInfos getIconGroupsByCreator(final long creatorId) {
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(creatorId);
        List<CreatorIconInfo> creatorIconInfos = new ArrayList<>();
        iconGroups.forEach(
                iconGroup ->
                {
                    int salesIconCount = paymentRepository.findAllByItemIdAndItemType(iconGroup.getId(), ItemType.ICON).size();
                    creatorIconInfos.add(
                            CreatorIconInfo.builder()
                                    .title(iconGroup.getName())
                                    .income(salesIconCount * iconGroup.getPrice())
                                    .salesCount(salesIconCount)
                                    .iconImageUrl(iconGroup.getIcons().stream().map(Icon::getIconImageUrl).toList())
                                    .build()
                    );
                }
        );

        return CreatorIconInfos.builder()
                .salesIconCount(creatorIconInfos.stream().mapToInt(CreatorIconInfo::salesCount).sum())
                .totalIncome(creatorIconInfos.stream().mapToInt(CreatorIconInfo::income).sum())
                .totalIconCount(iconGroupRepository.findAllByMemberId(creatorId).size())
                .creatorIconInfos(creatorIconInfos)
                .build();
    }

}