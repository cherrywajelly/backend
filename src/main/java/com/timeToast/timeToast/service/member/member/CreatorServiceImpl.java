package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.icon_group.ThumbnailIcon;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_CREATOR_INFO;
@Service
@Slf4j
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {
    private final MemberRepository memberRepository;
    private final CreatorAccountRepository creatorAccountRepository;
    private final MemberService memberService;
    private final IconGroupRepository iconGroupRepository;
    private final IconRepository iconRepository;
    private final PaymentRepository paymentRepository;


    @Transactional(readOnly = true)
    @Override
    public CreatorProfileResponse getCreatorProfile(final long memberId){
        Member member = memberRepository.getById(memberId);
        Optional<CreatorAccount> creatorAccount = creatorAccountRepository.findByMemberId(member.getId());

        if (creatorAccount.isPresent()) {
            CreatorInfoResponse creatorInfoResponse = CreatorInfoResponse.from(member.getNickname(), creatorAccount.get().getBank(), creatorAccount.get().getAccountNumber(), member.getMemberProfileUrl());
            IconGroupOrderedResponses iconGroupOrderedResponses = getIconOrderedResponse(memberId);

            long createdIconCount = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().count();
            long selledIconCount = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().mapToLong(IconGroupOrderedResponse::orderCount).sum();
            long settlement = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().mapToLong(IconGroupOrderedResponse::income).sum();

            return new CreatorProfileResponse(creatorInfoResponse, iconGroupOrderedResponses, createdIconCount, selledIconCount, settlement);
        } else {
            throw new NotFoundException(INVALID_CREATOR_INFO.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public IconGroupOrderedResponses getIconOrderedResponse (final long memberId) {

        List<IconGroupOrderedResponse> iconGroupOrderedResponses = new ArrayList<>();
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);

        iconGroups.forEach(iconGroup -> {
            List<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId());

            List<String> iconImageUrls = new ArrayList<>();
            icon.forEach(iconImage -> iconImageUrls.add(iconImage.getIconImageUrl()));


            List<Payment> payments = paymentRepository.findAllByItemId(iconGroup.getId());
            long income = payments.stream()
                    .mapToLong(Payment::getAmount)
                    .sum();

            Icon thumbnailIcon = iconRepository.findByIconGroupIdAndThumbnailIcon(iconGroup.getId(), ThumbnailIcon.THUMBNAILICON);

            iconGroupOrderedResponses.add(IconGroupOrderedResponse.of(iconGroup.getName(), thumbnailIcon.getIconImageUrl(), iconImageUrls, payments.size(), income, iconGroup.getIconState()));
        });
        return new IconGroupOrderedResponses(iconGroupOrderedResponses);
    }


    @Transactional
    @Override
    public CreatorInfoResponse putCreatorInfo(final long memberId, MultipartFile multipartFile, CreatorRequest creatorRequest) {
        Member member = memberRepository.getById(memberId);
        Optional<CreatorAccount> creatorAccount = creatorAccountRepository.findByMemberId(memberId);

        if(creatorAccount.isPresent() && member != null) {
            member.updateNickname(creatorRequest.nickname());
            MemberInfoResponse memberInfoResponse = memberService.saveProfileImageByLogin(memberId, multipartFile);
            creatorAccount.get().updateAccount(creatorRequest.creatorAccountResponse().bank(), creatorRequest.creatorAccountResponse().accountNumber());
            memberRepository.save(member);
            creatorAccountRepository.save(creatorAccount.get());

            return CreatorInfoResponse.from(creatorRequest.nickname(), creatorRequest.creatorAccountResponse().bank(), creatorRequest.creatorAccountResponse().accountNumber(), memberInfoResponse.profileUrl());
        } else {
            throw new BadRequestException(INVALID_CREATOR_INFO.getMessage());
        }
    }
}
