package com.timeToast.timeToast.repository.fcm;

import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.FCM_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class FcmRepositoryImpl implements FcmRepository {
    private final FcmJpaRepository fcmJpaRepository;

    @Override
    public Fcm save(final Fcm fcm) { return fcmJpaRepository.save(fcm); }

    @Override
    public List<Fcm> findByMemberIdOrderByCreatedAtDesc(final long memberId) {
        return fcmJpaRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }

    @Override
    public Fcm getById(final long id) { return fcmJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(FCM_NOT_FOUND.getMessage())); }
}

