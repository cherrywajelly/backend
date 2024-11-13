package com.timeToast.timeToast.repository.fcm;

import com.timeToast.timeToast.domain.fcm.Fcm;

import java.util.List;
import java.util.Optional;

public interface FcmRepository {
    Fcm save(final Fcm fcm);

    List<Fcm> findByMemberIdOrderByCreatedAtDesc(final long memberId);

    Fcm getById(final long id);

    void deleteAllByMemberId(final long memberId);
}
