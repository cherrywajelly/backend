package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest implements MemberService{

    @Override
    public MemberInfoResponse saveProfileImageByLogin(long memberId, MultipartFile profileImage) {
        return new MemberInfoResponse("nickname","profileUrl");
    }

    @Override
    public void postNickname(String nickname, long memberId) {

    }

    @Override
    public void nicknameValidation(String nickname) {

    }

    @Override
    public MemberInfoResponse getMemberInfo(long memberId) {
        return new MemberInfoResponse("nickname","profileUrl");
    }

    @Override
    public MemberProfileResponse getMemberProfile(long memberId) {
        return null;
    }
}