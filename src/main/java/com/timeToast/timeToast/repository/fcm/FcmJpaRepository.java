package com.timeToast.timeToast.repository.fcm;

import com.timeToast.timeToast.domain.fcm.Fcm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmJpaRepository extends JpaRepository<Fcm, Long> {
}
