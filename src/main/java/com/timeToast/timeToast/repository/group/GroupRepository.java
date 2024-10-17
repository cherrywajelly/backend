package com.timeToast.timeToast.repository.group;

import com.timeToast.timeToast.domain.group.Group;

import java.util.Optional;

public interface GroupRepository {
    Group save(final Group group);
    Optional<Group> findById(final long groupId);
    void deleteByGroupId(final long groupId);
}
