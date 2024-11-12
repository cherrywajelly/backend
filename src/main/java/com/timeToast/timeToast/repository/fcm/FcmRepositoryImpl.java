package com.timeToast.timeToast.repository.fcm;

import com.timeToast.timeToast.domain.fcm.Fcm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FcmRepositoryImpl implements FcmRepository {
    private final FcmJpaRepository fcmJpaRepository;

    @Override
    public Fcm save(final Fcm fcm) { return fcmJpaRepository.save(fcm); }

    @Override
    public List<Fcm> findByMemberId(final long memberId) {
        return fcmJpaRepository.findByMemberId(memberId);
    }
}
