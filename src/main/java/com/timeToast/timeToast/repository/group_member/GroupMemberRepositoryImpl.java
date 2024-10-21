package com.timeToast.timeToast.repository.group_member;

import com.timeToast.timeToast.domain.group_member.GroupMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    private final GroupMemberJpaRepository groupMemberJpaRepository;

    public GroupMemberRepositoryImpl(GroupMemberJpaRepository groupMemberJpaRepository) {
        this.groupMemberJpaRepository = groupMemberJpaRepository;
    }

    @Override
    public GroupMember save(final GroupMember groupMember) {
        return groupMemberJpaRepository.save(groupMember);
    }

    @Override
    public List<GroupMember> findAllByMemberId(final long memberId) {
        return groupMemberJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<GroupMember> findAllByMemberGroupId(final long memberGroupId) {
        return groupMemberJpaRepository.findAllByMemberGroupId(memberGroupId);
    }

    @Override
    public void delete(final GroupMember groupMember) {
        groupMemberJpaRepository.delete(groupMember);
    }
}
