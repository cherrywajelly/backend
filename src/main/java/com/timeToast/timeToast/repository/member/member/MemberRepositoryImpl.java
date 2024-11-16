package com.timeToast.timeToast.repository.member.member;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.global.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.timeToast.timeToast.domain.member.member.QMember.member;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_EXISTS;

import java.util.Optional;
import java.util.List;


@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository, final JPAQueryFactory queryFactory) {
        this.memberJpaRepository = memberJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public Member getById(final long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new BadRequestException(MEMBER_NOT_EXISTS.getMessage()));
    }

    @Override
    public Optional<Member> findById(final long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public List<Member> findMemberByNickname(final String nickname, final Pageable pageable){
        return queryFactory.selectFrom(member)
                .where(member.nickname.contains(nickname))
                .orderBy(
                        new CaseBuilder()
                                .when(member.nickname.startsWith(nickname)).then(0)
                                .when(member.nickname.contains(nickname)).then(1)
                                .otherwise(2).asc(),
                        member.nickname.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


    }

    @Override
    public List<Member> findAllByMemberRole(final MemberRole memberRole){
        return memberJpaRepository.findAllByMemberRole(memberRole);
    }

    @Override
    public void delete(final Member member) {
        memberJpaRepository.delete(member);
    }

    @Override
    public void deleteById(final long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    @Override
    public boolean existsByNickname(final String nickname) { return memberJpaRepository.existsByNickname(nickname);}
}
