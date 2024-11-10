package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.NICKNAME_CONFLICT;
import static com.timeToast.timeToast.global.constant.FileConstant.*;

import com.timeToast.timeToast.service.image.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final FileUploadService fileUploadService;
    private final IconRepository iconRepository;
    public MemberServiceImpl(final MemberRepository memberRepository, final FollowRepository followRepository,
                             final TeamMemberRepository teamMemberRepository, final FileUploadService fileUploadService,
                             final IconRepository iconRepository) {
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.fileUploadService = fileUploadService;
        this.iconRepository = iconRepository;
    }

    @Transactional
    @Override
    public MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage) {
        Member member = memberRepository.getById(memberId);
        String url = MEMBER.value() + SLASH.value() + IMAGE.value() + SLASH.value() + memberId;
        String profileImageUrl = fileUploadService.uploadImages(profileImage,url);
        member.updateProfileUrl(profileImageUrl);
        return new MemberInfoResponse(member.getNickname(), member.getMemberProfileUrl());
    }

    @Transactional
    @Override
    public void postNickname(final String nickname, final long memberId){

        Member member = memberRepository.getById(memberId);
        boolean exist = memberRepository.existsByNickname(nickname);

        if (exist) {
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        member.updateNickname(nickname);
    }

    @Transactional(readOnly = true)
    @Override
    public void nicknameValidation(final String nickname) {

        if(memberRepository.existsByNickname(nickname)){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }

    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponse getMemberInfo(final long memberId) {
        Member member = memberRepository.getById(memberId);
        return new MemberInfoResponse(member.getNickname(), getMemberProfileImage(member));
    }

    @Transactional(readOnly = true)
    @Override
    public MemberProfileResponse getMemberProfile(final long memberId) {
        Member member = memberRepository.getById(memberId);
        long followingCount = followRepository.findAllByFollowerId(memberId).stream().count();
        long followerCount = followRepository.findAllByFollowingId(memberId).stream().count();
        long teamCount = teamMemberRepository.findAllByMemberId(memberId).stream().count();

        return new MemberProfileResponse(member.getNickname(), getMemberProfileImage(member), followingCount, followerCount, teamCount);
    }

    private String getMemberProfileImage(final Member member){

        if (member.getMemberProfileUrl().equals(null)){
            return iconRepository.getDefaultIcon().getIconImageUrl();
        }
        return member.getMemberProfileUrl();
    }

}
