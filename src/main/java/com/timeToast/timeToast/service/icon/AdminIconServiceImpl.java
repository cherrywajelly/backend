package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.icon.response.IconGroupOrderInfo;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.repository.jpa.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import com.timeToast.timeToast.repository.jpa.payment.PaymentRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.LongStream;

import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;

@Service
@Slf4j
public class AdminIconServiceImpl implements AdminIconService {
    private final IconGroupRepository iconGroupRepository;
    private  final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final FileUploadService fileUploadService;
    private final IconMemberRepository iconMemberRepository;

    public AdminIconServiceImpl(final IconGroupRepository iconGroupRepository, final MemberRepository memberRepository,
                                final PaymentRepository paymentRepository, final FileUploadService fileUploadService,
                                final IconMemberRepository iconMemberRepository) {
        this.iconGroupRepository = iconGroupRepository;
        this.memberRepository = memberRepository;
        this.paymentRepository = paymentRepository;
        this.fileUploadService = fileUploadService;
        this.iconMemberRepository = iconMemberRepository;
    }

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;

    @Transactional
    @Override
    public IconGroupInfo postIconGroup(final MultipartFile thumbnailIcon, final List<MultipartFile> files,
                                       final IconGroupPostRequest iconGroupPostRequest, final long memberId) {

        Member creator = memberRepository.getById(memberId);

        IconGroup iconGroup = iconGroupRepository.save(iconGroupPostRequest.toEntity(iconGroupPostRequest, creator.getId()));

        String iconGroupUrl = baseUrl + ICON_GROUP.value() + SLASH.value() + iconGroup.getId() + SLASH.value() + IMAGE.value();
        String thumbnailImageUrl = fileUploadService.uploadfile(thumbnailIcon, iconGroupUrl);
        iconGroup.updateThumbnailImageUrl(thumbnailImageUrl);

        iconGroup.addIcons(postIconSet(files, iconGroup.getId()));

        log.info("save icon group ${}", iconGroup.getId());
        return IconGroupInfo.from(iconGroup, creator.getNickname());
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
    public IconGroupInfo saveIconState(final IconGroupStateRequest iconGroupStateRequest){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupStateRequest.iconGroupId());
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        iconGroup.updateIconState(iconGroupStateRequest.iconState());
        return IconGroupInfo.from(iconGroup, creator.getNickname());
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorIconGroupResponse getCreatorIconGroups(final long memberId){
        Member member = memberRepository.getById(memberId);

        List<CreatorIconGroup> creatorIconGroups = iconGroupRepository.findAllByMemberId(memberId).stream()
                .map(iconGroup -> getCreatorIconGroupByIconGroup(iconGroup, member.getNickname())).toList();

        return getCreatorIconGroupResponse(creatorIconGroups);

    }

    @Transactional(readOnly = true)
    @Override
    public CreatorIconGroup getCreatorIconGroup(final long memberId, final long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getByIdAndMemberId(memberId,iconGroupId);
        Member member = memberRepository.getById(memberId);

        return getCreatorIconGroupByIconGroup(iconGroup,member.getNickname());
    }


    private CreatorIconGroup getCreatorIconGroupByIconGroup(final IconGroup iconGroup, final String creatorName) {

        List<Payment> payments = paymentRepository.findAllByItemId(iconGroup.getId());
        long totalRevenue = payments.stream().mapToLong(Payment::getAmount).sum();

        List<IconResponse> iconResponses = iconGroup.getIcons().stream().map(IconResponse::from).toList();
        IconGroupOrderInfo iconGroupOrderInfo = new IconGroupOrderInfo(payments.size(),totalRevenue);

        return new CreatorIconGroup(IconGroupInfo.from(iconGroup,creatorName), iconResponses, iconGroupOrderInfo);
    }

    private CreatorIconGroupResponse getCreatorIconGroupResponse(List<CreatorIconGroup> creatorIconGroups){
        return CreatorIconGroupResponse.builder()
                .creatorIconGroups(creatorIconGroups)
                .totalIconCount(creatorIconGroups.size())
                .totalOrderCount(creatorIconGroups.stream().flatMapToLong(
                        t -> LongStream.of(t.iconGroupOrderInfo().orderCount())).sum())
                .totalIncome(creatorIconGroups.stream().flatMapToLong(
                        t -> LongStream.of(t.iconGroupOrderInfo().income())).sum())
                .totalSettlement((long) (creatorIconGroups.stream().flatMapToLong(
                        t->LongStream.of(t.iconGroupOrderInfo().income())).sum() * 0.7))
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public IconGroupDetail getIconGroupDetail(final long iconGroupId){
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconGroup.getIcons().stream().map(IconResponse::from).toList();
        IconGroupInfo iconGroupInfo = IconGroupInfo.from(iconGroup, creator.getNickname());
        return new IconGroupDetail(iconGroupInfo, iconResponses);

    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponses getMemberIconGroupInfo(final long memberId) {
        List<IconGroupDetail> iconGroupDetails = iconMemberRepository.findByMemberId(memberId).stream()
                .map(iconMember -> {

                    IconGroupInfo iconGroupInfo = iconGroupRepository.getIconGroupInfo(memberId, iconMember.getIconGroupId());
                    List<IconResponse> icons = iconGroupRepository.getById(iconMember.getIconGroupId())
                            .getIcons().stream().map(IconResponse::from).toList();

                    return new IconGroupDetail(iconGroupInfo, icons);
                })
                .toList();
        return new IconGroupDetailResponses(iconGroupDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfos getAllIconGroups(){
        List<IconGroup> iconGroups = iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN);
        List<IconGroupInfo> iconGroupInfos = iconGroups.stream().map(iconGroup -> {
            Member member = memberRepository.getById(iconGroup.getMemberId());
            return IconGroupInfo.from(iconGroup, member.getNickname());
        }).toList();

        return new IconGroupInfos(iconGroupInfos);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfos getIconGroupForNonApproval() {
        List<IconGroupInfo> iconGroupInfos = iconGroupRepository.findAllByIconState(IconState.WAITING)
                .stream().map(iconGroup -> {
                    Member member = memberRepository.getById(iconGroup.getMemberId());
                    return IconGroupInfo.from(iconGroup, member.getNickname());
                })
                .toList();

        return new IconGroupInfos(iconGroupInfos);
    }


}