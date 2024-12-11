package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class MemberIntegrationTest extends TestContainerSupport {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    public MemberIntegrationTest(final MemberRepository memberRepository, final MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @Test
    @DisplayName("사용자는 본인의 닉네임과 프로필 이미지를 조회하고 수정할 수 있다.")
    public void tryGetUserInfoAndEdit() {
        Member member = memberRepository.getById(1L);
        String newNickname = "new";

        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(member.getId());

        try {
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile profileImage = new MockMultipartFile(
                    "profileImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream());

            memberService.nicknameValidation(newNickname);
            MemberInfoResponse newNicknameInfoResponse = memberService.postNickname(newNickname, member.getId());
            MemberInfoResponse newProfileImageInfoResponse = memberService.saveProfileImageByLogin(member.getId(), profileImage);


            assertThat(memberInfoResponse.nickname()).isNotEqualTo(newNicknameInfoResponse.nickname());
            assertThat(memberInfoResponse.profileUrl()).isNotEqualTo(newProfileImageInfoResponse.profileUrl());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("사용자는 자신의 마이페이지를 조회할 수 있다.")
    public void tryToAccessMyPage() {
        Member member = memberRepository.getById(1L);

        MemberProfileResponse memberProfileResponse = memberService.getMemberProfileByLogin(member.getId());
        MemberPremium memberPremium = memberService.getMemberPremium(member.getId());

        assertThat(memberProfileResponse.nickname()).isEqualTo(member.getNickname());
        assertThat(memberPremium.premiumId()).isEqualTo(member.getPremiumId());
    }
}
