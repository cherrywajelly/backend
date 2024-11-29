package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.template.Template;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastTemplateResponse;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.template.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.yaml.snakeyaml.events.Event;

import java.time.LocalDate;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TemplateServiceImplTest {

    @Mock
    private TemplateRepository templateRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private TemplateServiceImpl templateService;

    private Template template;
    private EventToast eventToast;
    private Icon icon;
    private Member member;

    @BeforeEach
    void setUp() {
        long iconId = 1L;
        long memberId = 1L;

        template = Template.builder().build();
        eventToast = EventToast.builder().memberId(memberId).iconId(iconId).build();
        icon = Icon.builder().build();
        member = Member.builder().build();
    }

    @Test
    @DisplayName("새로운 공유 템플릿 저장 성공")
    void saveTemplate() {
        long memberId = 1L;
        TemplateSaveRequest templateSaveRequest = new TemplateSaveRequest(1L, "text");


        when(templateRepository.getByEventToastId(templateSaveRequest.eventToastId())).thenReturn(Optional.empty());
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Response response = templateService.saveTemplate(memberId, templateSaveRequest);

        verify(templateRepository, times(1)).save(any(Template.class));
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("공유 템플릿 새로운 텍스트 저장 성공")
    void saveTemplateUpdateText() {
        long memberId = 1L;
        TemplateSaveRequest templateSaveRequest = new TemplateSaveRequest(1L, "text");


        when(templateRepository.getByEventToastId(templateSaveRequest.eventToastId())).thenReturn(Optional.of(template));
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Response response = templateService.saveTemplate(memberId, templateSaveRequest);

        verify(templateRepository, times(1)).save(any(Template.class));
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("공유 템플릿 조회 성공")
    void getTemplate() {
        long eventToastId = 1L;
        EventToastTemplateResponse eventToastTemplateResponse = new EventToastTemplateResponse("title", LocalDate.of(2024, 1, 1));
        TemplateResponse templateResponse = new TemplateResponse(eventToastTemplateResponse, "iconImgUrl", "profileImgUrl", "nickname", "text");

        when(eventToastRepository.getById(eventToastId)).thenReturn(eventToast);
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);
        when(memberRepository.getById(eventToast.getMemberId())).thenReturn(member);
        when(templateRepository.getByEventToastId(eventToastId)).thenReturn(Optional.of(template));

        templateResponse = templateService.getTemplate(eventToastId);

        assertThat(templateResponse).isNotNull();
    }
}
