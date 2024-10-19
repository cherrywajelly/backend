package com.timeToast.timeToast.repository.group;

import com.timeToast.timeToast.domain.group.Group;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GroupRepositoryImpl implements GroupRepository{

    private final GroupJpaRepository groupJpaRepository;

    public GroupRepositoryImpl(GroupJpaRepository groupJpaRepository) {
        this.groupJpaRepository = groupJpaRepository;
    }

    @Override
    public Group save(final Group group){
        return groupJpaRepository.save(group);
    }

    @Override
    public Optional<Group> findById(final long groupId){
        return groupJpaRepository.findById(groupId);
    }

    @Override
    public void deleteByGroupId(final long groupId) {
        groupJpaRepository.deleteById(groupId);
    }

}
