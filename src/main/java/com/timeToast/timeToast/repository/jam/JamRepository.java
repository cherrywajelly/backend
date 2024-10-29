package com.timeToast.timeToast.repository.jam;

import com.timeToast.timeToast.domain.jam.Jam;

import java.util.List;


public interface JamRepository {

    Jam save(Jam jam);

    List<Jam> findByMemberId(final long memberId);

    List<Jam> findByEventToastId(final long eventToastId);

    Jam findByMemberIdAndEventToastId(final long memberId, final long eventToastId);
}