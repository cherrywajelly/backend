package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JamJpaRepository extends JpaRepository<Jam, Long> {
    List<Jam> findAllByMemberId(final long memberId);

    List<Jam> findAllByEventToastId(final long eventToastId);

    Optional<Jam> findByMemberIdAndEventToastId(final long memberId, final long eventToastId);
    void deleteAllByEventToastId(final long eventToastId);
}
