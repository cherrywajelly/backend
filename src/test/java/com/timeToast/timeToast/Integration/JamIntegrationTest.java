package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastMemberResponses;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponses;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class JamIntegrationTest extends TestContainerSupport {
    private final JamService jamService;
    private final MemberRepository memberRepository;
    private final EventToastService eventToastService;
    private final IconGroupService iconGroupService;

    @Autowired
    public JamIntegrationTest(final JamService jamService, final MemberRepository memberRepository,
                              final EventToastService eventToastService, final IconGroupService iconGroupService) {
        this.jamService = jamService;
        this.memberRepository = memberRepository;
        this.eventToastService = eventToastService;
        this.iconGroupService = iconGroupService;
    }

    @Test
    @DisplayName("사용자는 타사용자의 이벤트 토스트에 잼을 바를 수 있습니다.")
    public void tryToSpreadJamWithEventToast() {
        Member member1 = memberRepository.getById(1L);
        Member member2 = memberRepository.getById(2L);

        try {
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile jamData = new MockMultipartFile(
                    "jamImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream());

            EventToastMemberResponses eventToastMemberResponses = eventToastService.getMemberEventToastList(member1.getId(), member2.getId());
            eventToastMemberResponses.eventToastMemberResponses().forEach(
                    eventToastMemberResponse -> {
                        IconGroupMarketResponses iconGroupMarketResponses = iconGroupService.getAllJamsIconGroups(member1.getId());
                        IconGroupMarketDetailResponse iconGroupMarketDetailResponse = iconGroupService.getIconGroupDetail(member1.getId(), iconGroupMarketResponses.iconGroupMarketResponses().get(0).iconGroupId());

                        JamRequest jamRequest = new JamRequest("title", iconGroupMarketDetailResponse.iconResponses().get(0).iconId());
                        Response response = jamService.postJam(jamRequest, jamData, jamData, 1L, member1.getId());

                        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
                        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("사용자는 본인의 이벤트 토스트에 적힌 잼을 삭제할 수 있습니다.")
    public void tryToGetJamAndDelete() {
        Member member1 = memberRepository.getById(1L);

        EventToastOwnResponses eventToastOwnResponses = eventToastService.getOwnEventToastList(member1.getId());
        EventToastResponse eventToastResponse = eventToastService.getEventToast(member1.getId(), eventToastOwnResponses.eventToastOwnResponses().get(0).eventToastId());
        Response response = jamService.deleteJam(member1.getId(), eventToastResponse.jams().get(0).jamId());

        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
    }
}
