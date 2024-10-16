package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.follow.FollowResponses;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.util.BaseServiceTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FollowServiceImplTest extends BaseServiceTests {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FollowRepository followRepository;

    @Test
    public void saveFollow() {
        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);


        //when
        Follow follow = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember2.getId()).build();
        Follow saveFollow = followRepository.save(follow);


        //then
        assertThat(saveFollow.getFollowingId()).isEqualTo(saveMember1.getId());
        assertThat(saveFollow.getFollowerId()).isEqualTo(saveMember2.getId());

    }

    @Test
    public void findFollowerList() {

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();
        Member member3 =  Member.builder().nickname("nickname3").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);
        Member saveMember3 = memberRepository.save(member3);

        //when
        Follow follow1 = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember2.getId()).build();
        Follow follow2 = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember3.getId()).build();

        Follow saveFollow1 = followRepository.save(follow1);
        Follow saveFollow2 =followRepository.save(follow2);

        List<Follow> followers = followRepository.findAllByFollowingId(saveMember1.getId());

        //then
        assertThat(followers).hasSize(2);
        assertThat(followers).contains(saveFollow1);
        assertThat(followers).contains(saveFollow2);

    }


    @Test
    public void findFollowingList() {

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();
        Member member3 =  Member.builder().nickname("nickname3").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);
        Member saveMember3 = memberRepository.save(member3);

        //when
        Follow follow1 = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember3.getId()).build();
        Follow follow2 = Follow.builder().followingId(saveMember2.getId()).followerId(saveMember3.getId()).build();

        Follow saveFollow1 = followRepository.save(follow1);
        Follow saveFollow2 =followRepository.save(follow2);

        List<Follow> followings = followRepository.findAllByFollowerId(saveMember3.getId());

        //then
        assertThat(followings).hasSize(2);
        assertThat(followings).contains(saveFollow1);
        assertThat(followings).contains(saveFollow2);
    }


    @Test
    public void deleteFollowing() {

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);


        //when
        Follow follow = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember2.getId()).build();
        Follow saveFollow = followRepository.save(follow);

        assertThat(saveFollow.getFollowingId()).isEqualTo(follow.getFollowingId());
        assertThat(saveFollow.getFollowerId()).isEqualTo(follow.getFollowerId());

        Follow deleteFollow = followRepository.findByFollowingIdAndFollowerId(saveMember1.getId(), saveMember2.getId()).get();

        //then

        assertThat(deleteFollow.getFollowingId()).isEqualTo(saveMember1.getId());

       followRepository.deleteFollow(deleteFollow);
       Optional<Follow> findFollow = followRepository.findById(follow.getId());

       assertThat(findFollow).isEmpty();
    }

    @Test
    public void deleteFollower() {

        //given
        Member member1 =  Member.builder().nickname("nickname1").memberRole(MemberRole.USER).build();
        Member member2 =  Member.builder().nickname("nickname2").memberRole(MemberRole.USER).build();

        Member saveMember1 = memberRepository.save(member1);
        Member saveMember2 = memberRepository.save(member2);


        //when
        Follow follow = Follow.builder().followingId(saveMember1.getId()).followerId(saveMember2.getId()).build();
        Follow saveFollow = followRepository.save(follow);

        assertThat(saveFollow.getFollowingId()).isEqualTo(follow.getFollowingId());
        assertThat(saveFollow.getFollowerId()).isEqualTo(follow.getFollowerId());

        Follow deleteFollow = followRepository.findByFollowingIdAndFollowerId(saveMember1.getId(), saveMember2.getId()).get();

        //then

        assertThat(deleteFollow.getFollowerId()).isEqualTo(saveMember2.getId());

        followRepository.deleteFollow(deleteFollow);
        Optional<Follow> findFollow = followRepository.findById(follow.getId());

        assertThat(findFollow).isEmpty();
    }

}
