package com.timeToast.timeToast.repository.member_group.member_group;

import com.timeToast.timeToast.domain.team.team.Team;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberGroupRepositoryImpl implements MemberGroupRepository {

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public MemberGroupRepositoryImpl(MemberGroupJpaRepository memberGroupJpaRepository) {
        this.memberGroupJpaRepository = memberGroupJpaRepository;
    }

    @Override
    public Team save(final Team memberGroup){
        return memberGroupJpaRepository.save(memberGroup);
    }

    @Override
    public Team getById(final long memberGroupId) {
        return memberGroupJpaRepository.getMemberGroupById(memberGroupId);
    }

    @Override
    public Optional<Team> findById(final long memberGroupId){
        return memberGroupJpaRepository.findById(memberGroupId);
    }

    @Override
    public void deleteByMemberGroupId(final long memberGroupId) {
        memberGroupJpaRepository.deleteById(memberGroupId);
    }

}
