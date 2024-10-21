package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberGroupRepositoryImpl implements MemberGroupRepository {

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public MemberGroupRepositoryImpl(MemberGroupJpaRepository memberGroupJpaRepository) {
        this.memberGroupJpaRepository = memberGroupJpaRepository;
    }

    @Override
    public MemberGroup save(final MemberGroup memberGroup){
        return memberGroupJpaRepository.save(memberGroup);
    }

    @Override
    public MemberGroup getById(final long memberGroupId) {
        return memberGroupJpaRepository.getMemberGroupById(memberGroupId);
    }

    @Override
    public Optional<MemberGroup> findById(final long memberGroupId){
        return memberGroupJpaRepository.findById(memberGroupId);
    }

    @Override
    public void deleteByMemberGroupId(final long memberGroupId) {
        memberGroupJpaRepository.deleteById(memberGroupId);
    }

}
