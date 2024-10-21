package com.timeToast.timeToast.repository.group_member;

import com.timeToast.timeToast.domain.group_member.GroupMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    private final GroupMemberJpaRepository memberGroupJpaRepository;

    public GroupMemberRepositoryImpl(GroupMemberJpaRepository memberGroupJpaRepository) {
        this.memberGroupJpaRepository = memberGroupJpaRepository;
    }

    @Override
    public GroupMember save(final GroupMember groupMember) {
        return memberGroupJpaRepository.save(groupMember);
    }

    @Override
    public List<GroupMember> findAllByMemberId(final long memberId) {
        return memberGroupJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<GroupMember> findAllByGroupId(final long groupId) {
        return memberGroupJpaRepository.findAllByGroupId(groupId);
    }

    @Override
    public void delete(final GroupMember groupMember) {
        memberGroupJpaRepository.delete(groupMember);
    }
}
