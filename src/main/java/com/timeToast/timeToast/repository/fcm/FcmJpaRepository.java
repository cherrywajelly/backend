package com.timeToast.timeToast.repository.fcm;

import com.timeToast.timeToast.domain.fcm.Fcm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FcmJpaRepository extends JpaRepository<Fcm, Long> {
    List<Fcm> findByMemberId(final long memberId);
}