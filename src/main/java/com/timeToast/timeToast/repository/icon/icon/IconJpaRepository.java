package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconJpaRepository extends JpaRepository<Icon, Long> {
    List<Icon> findAllByIconGroupId(final long iconGroupId);
}
