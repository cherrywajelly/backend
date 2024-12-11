package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupResponses;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
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

import java.time.LocalDate;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class IconIntegrationTest extends TestContainerSupport {
    private final IconGroupService iconGroupService;
    private final MemberRepository memberRepository;
    private final EventToastService eventToastService;
    private final JamService jamService;

    @Autowired
    public IconIntegrationTest(final IconGroupService iconGroupService, final MemberRepository memberRepository,
                               final EventToastService eventToastService, final JamService jamService) {
        this.iconGroupService = iconGroupService;
        this.memberRepository = memberRepository;
        this.eventToastService = eventToastService;
        this.jamService = jamService;
    }

    @Test
    @DisplayName("사용자는 본인이 소유한 아이콘 중 하나를 선택해 이벤트 토스트를 생성 수 있습니다.")
    public void tryToWriteToastPiece() {
        Member member = memberRepository.getById(1L);

        IconGroupResponses iconGroupResponses = iconGroupService.getToastIconGroups(member.getId());

        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2025, 1, 1), "title", iconGroupResponses.iconGroupResponses().get(0).icon().get(0).iconId(), "description");
        ResponseWithId responseWithId = eventToastService.saveEventToast(eventToastPostRequest, member.getId());
        assertThat(responseWithId).isNotNull();
    }


    @Test
    @DisplayName("사용자는 아이콘 마켓에서 잼 아이콘을 구매해 잼을 바를 수 있습니다.")
    public void tryToBuyJamIcon() {
        Member member1 = memberRepository.getById(1L);

        try {
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile iconData = new MockMultipartFile(
                    "iconImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream());

            IconGroupMarketResponses iconGroupMarketResponses = iconGroupService.getAllJamsIconGroups(member1.getId());
            IconGroupMarketDetailResponse iconGroupMarketDetailResponse = iconGroupService.getIconGroupDetail(member1.getId(), iconGroupMarketResponses.iconGroupMarketResponses().get(0).iconGroupId());
            JamRequest jamRequest = new JamRequest("title", iconGroupMarketDetailResponse.iconResponses().get(0).iconId());
            Response response = jamService.postJam(jamRequest, iconData, iconData, 1L, member1.getId());

            assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
            assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
