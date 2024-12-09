package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.service.showcase.ShowcaseService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class MemberIntegrationTest extends TestContainerSupport {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ShowcaseService showcaseService;
    private final EventToastService eventToastService;

    @Autowired
    public MemberIntegrationTest(final MemberRepository memberRepository, final MemberService memberService, final ShowcaseService showcaseService, final EventToastService eventToastService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.showcaseService = showcaseService;
        this.eventToastService = eventToastService;
    }

    @Test
    @DisplayName("사용자는 본인의 닉네임과 프로필 이미지를 조회하고 수정할 수 있다.")
    public void tryGetUserInfoAndEdit() {
        Member member = memberRepository.getById(1L);

        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(member.getId());
        assertThat(memberInfoResponse.nickname()).isEqualTo(member.getNickname());
        assertThat(memberInfoResponse.profileUrl()).isEqualTo(member.getMemberProfileUrl());

        MemberProfileResponse memberProfileResponse = memberService.getMemberProfileByLogin(member.getId());
        Response response = memberService.nicknameValidation("nickname");
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        MemberInfoResponse updatedNicknameResponse = memberService.postNickname("nickname", member.getId());

        try {
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile memberImages = new MockMultipartFile(
                    "memberImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream());

            MemberInfoResponse updatedProfileImage = memberService.saveProfileImageByLogin(member.getId(), memberImages);

            assertThat(updatedNicknameResponse.nickname()).isEqualTo("nickname");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("사용자는 자신의 마이페이지를 조회할 수 있다.")
    public void tryToAccessMyPage() {
        Member member = memberRepository.getById(1L);

        MemberProfileResponse memberProfileResponse = memberService.getMemberProfileByLogin(member.getId());
        ShowcaseResponses showcaseResponses = showcaseService.getShowcase(member.getId());
        EventToastOwnResponses eventToastOwnResponses = eventToastService.getOwnEventToastList(member.getId());

        eventToastOwnResponses.eventToastOwnResponses().forEach(
                eventToastOwnResponse -> {
                    assertThat(eventToastOwnResponse.openedDate()).isInTheFuture();
                }
        );
        assertThat(memberProfileResponse.nickname()).isEqualTo(member.getNickname());
    }
}
