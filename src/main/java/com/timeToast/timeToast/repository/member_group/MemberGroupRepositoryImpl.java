package com.timeToast.timeToast.repository.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberGroupRepositoryImpl implements MemberGroupRepository {

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public MemberGroupRepositoryImpl(MemberGroupJpaRepository memberGroupJpaRepository) {
        this.memberGroupJpaRepository = memberGroupJpaRepository;
    }

    @Override
    public MemberGroup save(final MemberGroup memberGroup) {
        return memberGroupJpaRepository.save(memberGroup);
    }

    @Override
    public List<MemberGroup> findAllByMemberId(final long memberId) {
        return memberGroupJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<MemberGroup> findAllByGroupId(final long groupId) {
        return memberGroupJpaRepository.findAllByGroupId(groupId);
    }

    @Override
    public void delete(final MemberGroup memberGroup) {
        memberGroupJpaRepository.delete(memberGroup);
    }
}
