package com.timeToast.timeToast.repository.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IconGroupJpaRepository extends JpaRepository<IconGroup, Long> {
    List<IconGroup> findByIconType(final IconType iconType);
}
