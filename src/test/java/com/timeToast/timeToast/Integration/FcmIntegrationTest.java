package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_PUT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class FcmIntegrationTest extends TestContainerSupport {
    private final FcmService fcmService;
    private final MemberRepository memberRepository;

    @Autowired
    public FcmIntegrationTest(final FcmService fcmService, final MemberRepository memberRepository) {
        this.fcmService = fcmService;
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("사용자는 알림 목록에서 알림을 선택하여 관련 상세페이지로 이동할 수 있다.")
    public void tryToGetAlarm() {
        Member member = memberRepository.getById(1L);

        FcmResponses fcmResponses = fcmService.getFcmResponses(member.getId());
        fcmResponses.fcmResponses().forEach(
                fcmResponse -> {
                    Response response = fcmService.putIsOpened(member.getId(), fcmResponse.fcmId());
                    assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
                    assertThat(response.message()).isEqualTo(SUCCESS_PUT.getMessage());
                }
        );
    }
}
