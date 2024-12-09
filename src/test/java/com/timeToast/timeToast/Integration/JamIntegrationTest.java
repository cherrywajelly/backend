package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
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
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class JamIntegrationTest extends TestContainerSupport {
    private final JamService jamService;
    private final MemberRepository memberRepository;
    private final EventToastRepository eventToastRepository;
    private final EventToastService eventToastService;

    @Autowired
    public JamIntegrationTest(final JamService jamService, final MemberRepository memberRepository, final EventToastRepository eventToastRepository, final EventToastService eventToastService) {
        this.jamService = jamService;
        this.memberRepository = memberRepository;
        this.eventToastRepository = eventToastRepository;
        this.eventToastService = eventToastService;
    }

//    @Test
//    @DisplayName("사용자는 타사용자의 이벤트 토스트에 잼을 바를 수 있습니다.")
//    public void tryToSpreadJamWithEventToast() {
//        Member member1 = memberRepository.getById(1L);
//        Member member2 = memberRepository.getById(2L);
//
//        EventToast eventToast = eventToastRepository.getById(2L);
//
//        JamRequest jamRequest = new JamRequest("title", 1L);
//        try {
//            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
//            MockMultipartFile jamContents = new MockMultipartFile(
//                    "jamContents",
//                    imageResource.getFilename(),
//                    "contents/jpeg",
//                    imageResource.getInputStream());
//
//            Response response = jamService.postJam(jamRequest, jamContents, jamContents, eventToast.getId(), member2.getId());
//            assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
//            assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    @DisplayName("사용자는 본인의 이벤트 토스트에 적힌 잼을 삭제할 수 있습니다.")
//    public void tryToGetJamAndDelete() {
//        Member member1 = memberRepository.getById(1L);
//        Member member2 = memberRepository.getById(2L);
//
//        EventToastOwnResponse eventToastOwnResponse = eventToastService.getOwnEventToastList(member1.getId()).eventToastOwnResponses().get(0);
//        EventToast eventToast = eventToastRepository.getById(eventToastOwnResponse.eventToastId());
//        JamRequest jamRequest = new JamRequest("title", 1L);
//        try {
//            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
//            MockMultipartFile jamContents = new MockMultipartFile(
//                    "jamContents",
//                    imageResource.getFilename(),
//                    "contents/jpeg",
//                    imageResource.getInputStream());
//
//            jamService.postJam(jamRequest, jamContents, jamContents, eventToast.getId(), member1.getId());
//
//            Response response = jamService.deleteJam(member1.getId(), 1L);
//            assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
//            assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
