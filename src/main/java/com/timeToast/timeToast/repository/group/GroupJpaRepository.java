package com.timeToast.timeToast.repository.group;

import com.timeToast.timeToast.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupJpaRepository extends JpaRepository<Group, Long> {

    Group getGroupById(final long groupId);
}
