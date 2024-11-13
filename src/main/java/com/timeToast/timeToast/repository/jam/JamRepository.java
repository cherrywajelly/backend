package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;

import java.util.List;


public interface JamRepository {

    Jam save(Jam jam);

    List<Jam> findAllByMemberId(final long memberId);

    List<Jam> findAllByEventToastId(final long eventToastId);

    Jam findByMemberIdAndEventToastId(final long memberId, final long eventToastId);

    void deleteAllByEventToastId(final long eventToastId);
}