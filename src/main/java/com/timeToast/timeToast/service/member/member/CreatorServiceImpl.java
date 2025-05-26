//package com.timeToast.timeToast.service.member.member;
//
//import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
//import com.timeToast.timeToast.domain.member.member.Member;
//import com.timeToast.timeToast.domain.payment.Payment;
//import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
//import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOrderedResponse;
//import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOrderedResponses;
//import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
//import com.timeToast.timeToast.global.exception.NotFoundException;
//import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
//import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
//import com.timeToast.timeToast.repository.member.member.MemberRepository;
//import com.timeToast.timeToast.repository.payment.PaymentRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_CREATOR_INFO;
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class CreatorServiceImpl implements CreatorService {
//    private final MemberRepository memberRepository;
//    private final CreatorAccountRepository creatorAccountRepository;
//    private final MemberService memberService;
//    private final IconGroupRepository iconGroupRepository;
//    private final PaymentRepository paymentRepository;
//
//
//    @Transactional(readOnly = true)
//    @Override
//    public CreatorProfileResponse getCreatorProfile(final long memberId){
//        Member member = memberRepository.getById(memberId);
//        Optional<CreatorAccount> creatorAccount = creatorAccountRepository.findByMemberId(member.getId());
//
//        if (creatorAccount.isPresent()) {
//            CreatorInfoResponse creatorInfoResponse = CreatorInfoResponse.from(member.getNickname(), creatorAccount.get().getBank().value(), creatorAccount.get().getAccountNumber(), member.getMemberProfileUrl());
//            IconGroupOrderedResponses iconGroupOrderedResponses = getIconOrderedResponse(memberId);
//
//            long createdIconCount = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().count();
//            long selledIconCount = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().mapToLong(IconGroupOrderedResponse::orderCount).sum();
//            long revenue = iconGroupOrderedResponses.iconGroupOrderedResponses().stream().mapToLong(IconGroupOrderedResponse::income).sum();
//            long settlement = (long) (revenue * 0.7);
//
//            return new CreatorProfileResponse(creatorInfoResponse, iconGroupOrderedResponses, createdIconCount, selledIconCount, revenue, settlement);
//        } else {
//            throw new NotFoundException(INVALID_CREATOR_INFO.getMessage());
//        }
//    }
//
//
//
//}
