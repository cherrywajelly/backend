package com.timeToast.timeToast.service.template;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.template.Template;
import com.timeToast.timeToast.dto.event_toast.response.EventToastTemplateResponse;
import com.timeToast.timeToast.dto.template.response.TemplateResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.template.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public TemplateResponse getTemplate(final long eventToastId) {

        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Icon icon = iconRepository.getById(eventToast.getIconId());
        Member member = memberRepository.getById(eventToast.getMemberId());

        Optional<Template> template = templateRepository.getByEventToastId(eventToastId);
        String text = null;

        if (template.isPresent()) { text = template.get().getTemplateText(); }

        EventToastTemplateResponse eventToastTemplateResponse = new EventToastTemplateResponse(eventToast.getTitle(), eventToast.getOpenedDate());
        TemplateResponse templateResponse = new TemplateResponse(eventToastTemplateResponse, icon.getIconImageUrl(), member.getMemberProfileUrl(), member.getNickname(), text);
        return templateResponse;
    }
}
