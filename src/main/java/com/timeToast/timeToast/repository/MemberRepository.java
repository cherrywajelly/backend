package com.timeToast.timeToast.repository;

import com.timeToast.timeToast.domain.member.Member;

public interface MemberRepository {

    Member getById(final long memberId);
    Member save(final Member member);
    void delete(final Member member);
}
