package com.timeToast.timeToast.repository.showcase;

import com.timeToast.timeToast.domain.showcase.Showcase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowcaseJpaRepository extends JpaRepository<Showcase, Long> {
    List<Showcase> findAllByMemberId(final long memberId);
    void deleteAllByEventToastId(final long eventToastId);
    void deleteAllByMemberId(final long memberId);
}
