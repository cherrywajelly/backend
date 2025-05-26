package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.showcase.Showcase;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
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

        log.info("save showcase by {}", memberId);

        return new ShowcaseSaveResponses(showcaseSaveResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ShowcaseEditResponses getShowcaseSaveList(final long memberId) {
        List<Showcase> showcases = showcaseRepository.findAllByMemberId(memberId);
        List<ShowcaseEditResponse> showcaseEditResponses = new ArrayList<>();

        eventToastRepository.findAllByMemberId(memberId).stream().sorted(Comparator.comparing(EventToast::getCreatedAt).reversed()).forEach(
                eventToast -> {

                    String iconUrl = iconRepository.getById(eventToast.getIconId()).getIconImageUrl();

                    Optional<Showcase> showcase = showcases.stream().filter(findShowcase -> findShowcase.getEventToastId().equals(eventToast.getId())).findFirst();
                    if(showcase.isPresent()){
                        showcaseEditResponses.add(
                                ShowcaseEditResponse.from(eventToast, iconUrl, true, showcase.get().getId()));
                    }else{
                        showcaseEditResponses.add(
                                ShowcaseEditResponse.from(eventToast, iconUrl, false,  null));
                    }

                }
        );


        return new ShowcaseEditResponses(showcaseEditResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ShowcaseResponses getShowcase(final long memberId) {
        List<ShowcaseResponse> showcaseResponses = new ArrayList<>();

        showcaseRepository.findAllByMemberId(memberId).forEach(
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
    public Response deleteShowcase(final long memberId, final long showcaseId) {

        Showcase showcase = showcaseRepository.findByShowcaseId(showcaseId)
                .orElseThrow(() -> new NotFoundException(SHOWCASE_NOT_FOUND.getMessage()));

        if(!showcase.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_SHOWCASE.getMessage());
        }

        showcaseRepository.deleteShowcase(showcase);
        log.info("delete showcase {} by {}", showcaseId, memberId);
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

}
