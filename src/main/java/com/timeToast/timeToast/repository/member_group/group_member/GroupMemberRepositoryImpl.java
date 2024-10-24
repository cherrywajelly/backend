package com.timeToast.timeToast.repository.member_group.group_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupMemberRepositoryImpl implements GroupMemberRepository {

    private final GroupMemberJpaRepository groupMemberJpaRepository;

    public GroupMemberRepositoryImpl(GroupMemberJpaRepository groupMemberJpaRepository) {
        this.groupMemberJpaRepository = groupMemberJpaRepository;
    }

    @Override
    public TeamMember save(final TeamMember groupMember) {
        return groupMemberJpaRepository.save(groupMember);
    }

    @Override
    public List<TeamMember> findAllByMemberId(final long memberId) {
        return groupMemberJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<TeamMember> findAllByMemberGroupId(final long memberGroupId) {
        return groupMemberJpaRepository.findAllByMemberGroupId(memberGroupId);
    }

    @Override
    public void delete(final TeamMember groupMember) {
        groupMemberJpaRepository.delete(groupMember);
    }
}
