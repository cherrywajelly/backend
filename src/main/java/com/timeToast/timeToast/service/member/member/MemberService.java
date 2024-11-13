package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage);
    void postNickname(final String nickname, final long memberId);
    void nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfileByLogin(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
}
