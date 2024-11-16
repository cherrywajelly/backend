package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;

import java.util.List;
import java.util.Optional;


public interface JamRepository {

    Jam save(Jam jam);

    List<Jam> findAllByMemberId(final long memberId);

    List<Jam> findAllByEventToastId(final long eventToastId);

    Optional<Jam> findByMemberIdAndEventToastId(final long memberId, final long eventToastId);

    Jam getById(final long jamId);

    void deleteById(final long jamId);

    void deleteAllByEventToastId(final long eventToastId);
}