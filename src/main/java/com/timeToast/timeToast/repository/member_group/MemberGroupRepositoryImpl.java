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
    public MemberGroup getById(final long groupId) {
        return memberGroupJpaRepository.getGroupById(groupId);
    }

    @Override
    public Optional<MemberGroup> findById(final long groupId){
        return memberGroupJpaRepository.findById(groupId);
    }

    @Override
    public void deleteByGroupId(final long groupId) {
        memberGroupJpaRepository.deleteById(groupId);
    }

}
