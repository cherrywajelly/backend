package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_CREATOR_INFO;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {
    private final MemberRepository memberRepository;
    private final CreatorAccountRepository creatorAccountRepository;
    private final MemberService memberService;

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
