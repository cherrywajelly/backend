package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.showcase.Showcase;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Service
public class ShowcaseServiceImpl implements ShowcaseService{

    private final ShowcaseRepository showcaseRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;

    public ShowcaseServiceImpl(final ShowcaseRepository showcaseRepository, final EventToastRepository eventToastRepository,
                               final IconRepository iconRepository) {
        this.showcaseRepository = showcaseRepository;
        this.eventToastRepository = eventToastRepository;
        this.iconRepository = iconRepository;
    }

    @Transactional
    @Override
    public ShowcaseSaveResponses saveShowcase(final long memberId, final ShowcaseSaveRequest showcaseSaveRequest) {
        List<Showcase> memberShowcase = showcaseRepository.findAllByMemberId(memberId);

        if(memberShowcase.size()+ showcaseSaveRequest.showcases().size() > 3){
            throw new BadRequestException(INVALID_SHOWCASE_COUNT.getMessage());
        }

        List<String> showcaseSaveResponses = new ArrayList<>();

        showcaseSaveRequest.showcases().forEach(
                showcaseId -> {
                    EventToast findEventToast = eventToastRepository.getById(showcaseId);
                    showcaseRepository.save(
                            Showcase.builder()
                                    .eventToastId(showcaseId)
                                    .memberId(memberId)
                                    .build());
                    showcaseSaveResponses.add(findEventToast.getTitle());
                }
        );

        return new ShowcaseSaveResponses(showcaseSaveResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ShowcaseEditResponses getShowcaseSaveList(final long memberId) {
        List<EventToast> eventToasts = eventToastRepository.findByMemberId(memberId);
        List<Showcase> showcases = showcaseRepository.findAllByMemberId(memberId);
        List<ShowcaseEditResponse> showcaseEditResponses = new ArrayList<>();

        eventToasts.forEach(
                eventToast -> {
                    boolean isShowcase = showcases.stream()
                            .filter(showcase -> showcase.getId().equals(eventToast.getId())).findFirst().isPresent();

                    String iconUrl = iconRepository.getById(eventToast.getIconId()).getIconImageUrl();

                    showcaseEditResponses.add(
                            ShowcaseEditResponse.from(eventToast, iconUrl, isShowcase));
                }
        );


        return new ShowcaseEditResponses(showcaseEditResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ShowcaseResponses getShowcase(final long memberId) {
        List<Showcase> showcases = showcaseRepository.findAllByMemberId(memberId);
        List<ShowcaseResponse> showcaseResponses = new ArrayList<>();

        showcases.forEach(
                showcase -> {
                    EventToast eventToast = eventToastRepository.getById(showcase.getEventToastId());
                    String iconUrl = iconRepository.getById(eventToast.getIconId()).getIconImageUrl();
                    showcaseResponses.add(new ShowcaseResponse(eventToast.getId(), iconUrl));
                }
        );

        return new ShowcaseResponses(showcaseResponses);
    }

    @Transactional
    @Override
    public void deleteShowcase(final long memberId, final long showcaseId) {

        Showcase showcase = showcaseRepository.findByShowcaseId(showcaseId)
                .orElseThrow(() -> new NotFoundException(SHOWCASE_NOT_FOUND.getMessage()));

        if(!showcase.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_SHOWCASE.getMessage());
        }

        showcaseRepository.deleteShowcase(showcase);

    }
}
