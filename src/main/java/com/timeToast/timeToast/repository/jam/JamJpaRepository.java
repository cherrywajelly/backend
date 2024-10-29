package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JamJpaRepository extends JpaRepository<Jam, Long> {
    List<Jam> findByMemberId(final long memberId);

    Jam findByMemberIdAndEventToastId(final long memberId, final long eventToastId);
}
