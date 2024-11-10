package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.domain.showcase.Showcase;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowcaseServiceImpl implements ShowcaseService{

    private final ShowcaseRepository showcaseRepository;

    public ShowcaseServiceImpl(final ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }

    @Override
    public ShowcaseSaveResponse saveShowcase(final long memberId, final ShowcaseSaveRequest showcaseSaveRequest) {
        return null;
    }

    @Override
    public ShowcaseSaveResponses getShowcaseSaveList(final long memberId) {
        return null;
    }

    @Override
    public ShowcaseResponses getShowcase(final long memberId) {
        return null;
    }

    @Override
    public void deleteShowcase(final long showcaseId) {

    }
}
