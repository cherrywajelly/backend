package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.fcm.FcmServiceImpl;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class FcmIntegrationTest extends TestContainerSupport {
    private final FcmService fcmService;
    private final FcmServiceImpl fcmServiceImpl;
    private final MemberRepository memberRepository;
    private final EventToastService eventToastService;
    private final EventToastRepository eventToastRepository;

    @Autowired
    public FcmIntegrationTest(final FcmService fcmService, final MemberRepository memberRepository, final FcmServiceImpl fcmServiceImpl,
                              final EventToastService eventToastService, final EventToastRepository eventToastRepository) {
        this.fcmService = fcmService;
        this.memberRepository = memberRepository;
        this.fcmServiceImpl = fcmServiceImpl;
        this.eventToastService = eventToastService;
        this.eventToastRepository = eventToastRepository;
    }

    @Test
    @DisplayName("사용자는 알림 목록에서 알림을 선택하여 관련 상세페이지로 이동할 수 있습니다.")
    public void tryToGetAlarm() {
        Member member = memberRepository.getById(1L);

        EventToast eventToast = eventToastRepository.getById(1L);

        FcmPostRequest fcmPostRequest = new FcmPostRequest(FcmConstant.EVENTTOASTOPENED, 1L, "toastName", eventToast.getId());
        fcmServiceImpl.saveFcmInfo(member.getId(), fcmPostRequest);

        FcmResponses fcmResponses = fcmService.getFcmResponses(member.getId());
        Response response = fcmService.putIsOpened(member.getId(), fcmResponses.fcmResponses().get(0).fcmId());

        assertThat(fcmResponses).isNotNull();
        assertThat(fcmResponses.fcmResponses()).hasSize(1);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
    }
}
