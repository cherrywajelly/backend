package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IconJpaRepository extends JpaRepository<Icon, Long> {
    List<Icon> findByIconGroupId(final long iconGroupId);
}
