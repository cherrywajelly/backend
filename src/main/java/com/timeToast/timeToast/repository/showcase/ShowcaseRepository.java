package com.timeToast.timeToast.repository.showcase;

import com.timeToast.timeToast.domain.showcase.Showcase;

import java.util.List;
import java.util.Optional;

public interface ShowcaseRepository {
    Showcase save(final Showcase showcase);
    Optional<Showcase> findByShowcaseId(final long showcaseId);
    List<Showcase> findAllByMemberId(final long memberId);
    void deleteShowcase(final Showcase showcase);
    void deleteAllByEventToastId(final long eventToastId);
}
