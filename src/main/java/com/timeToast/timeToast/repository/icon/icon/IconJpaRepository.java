package com.timeToast.timeToast.repository.icon.icon;

import com.timeToast.timeToast.domain.enums.icon_group.ThumbnailIcon;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconJpaRepository extends JpaRepository<Icon, Long> {
    List<Icon> findAllByIconGroupId(final long iconGroupId);
    Icon findByIconGroupIdAndThumbnailIcon(final long iconGroupId, final ThumbnailIcon thumbnailIcon);
}
