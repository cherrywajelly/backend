package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponses;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.event_toast.EventToastServiceImpl;
import com.timeToast.timeToast.service.template.TemplateService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class TemplateIntegrationTest extends TestContainerSupport {
    private final EventToastService eventToastService;
    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final TemplateService templateService;

    @Autowired
    public TemplateIntegrationTest(final EventToastService eventToastService, final MemberRepository memberRepository,
                                     final EventToastServiceImpl eventToastServiceImpl, final TemplateService templateService,
                                   final EventToastRepository eventToastRepository) {
        this.eventToastService = eventToastService;
        this.memberRepository = memberRepository;
        this.eventToastRepository = eventToastRepository;
        this.templateService = templateService;
    }

    @Test
    @DisplayName("사용자는 이벤트 토스트의 공유 템플릿을 생성할 수 있습니다.")
    public void tryToCreateTemplate() {
        Member member = memberRepository.getById(1L);

        EventToastOwnResponses eventToastOwnResponses = eventToastService.getOwnEventToastList(member.getId());

        EventToast eventToast = eventToastRepository.getById(eventToastOwnResponses.eventToastOwnResponses().stream().findFirst().get().eventToastId());

        TemplateSaveRequest templateSaveRequest = new TemplateSaveRequest(eventToast.getId(), "text");
        Response response = templateService.saveTemplate(member.getId(), templateSaveRequest);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }
}
