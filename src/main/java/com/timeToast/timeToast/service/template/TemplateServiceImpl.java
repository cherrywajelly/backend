package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.template.Template;
import com.timeToast.timeToast.dto.event_toast.response.EventToastTemplateResponse;
import com.timeToast.timeToast.dto.template.request.TemplateSaveRequest;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.template.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Response saveTemplate(final long memberId, final long eventToastId, final String text) {
        Optional<Template> template = templateRepository.getByEventToastId(eventToastId);

        if (template.isPresent()) {
            template.get().updateTemplateText(text);
            templateRepository.save(template.get());
        } else {
            Template newTemplate = TemplateSaveRequest.toEntity(memberId, eventToastId, text);
            templateRepository.save(newTemplate);
        }

        log.info("save new template for event toast {}", eventToastId);
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    public TemplateResponse getTemplate(final long eventToastId) {

        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Icon icon = iconRepository.getById(eventToast.getIconId());
        Member member = memberRepository.getById(eventToast.getMemberId());
        Optional<Template> template = templateRepository.getByEventToastId(eventToastId);

        String text = null;

        if (template.isEmpty()) {
            text = template.get().getTemplateText();
        }

        EventToastTemplateResponse eventToastTemplateResponse = new EventToastTemplateResponse(eventToast.getTitle(), eventToast.getOpenedDate());
        return TemplateResponse.of(eventToastTemplateResponse, icon.getIconImageUrl(), member.getMemberProfileUrl(), member.getNickname(), text);
    }
}
