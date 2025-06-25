package com.timeToast.timeToast.service.follow;

import com.timeToast.timeToast.domain.enums.follow.FollowType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class FollowServiceImplTest{

    @Mock
    FollowRepository followRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    FcmService fcmService;

    @InjectMocks
    FollowServiceImpl followService;

    private Follow setUpFollow(){
        return Follow.builder()
                .followingId(1L)
                .followerId(2L).build();

    }

    private Member setUpMember() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.USER)
                .build();
    }

    private List<Follow> setUpFollowersList(final long memberId) {
        List<Follow> followers = new ArrayList<>();
        for(long i = 2; i <= 10; i++) {
            Follow follow =  Follow.builder().followingId(memberId).followerId(i).build();
            ReflectionTestUtils.setField(follow, "id", i);
            followers.add(follow);
        }
        return followers;
    }

    private List<Follow> setUpFollowingList(final long memberId) {
        List<Follow> followings = new ArrayList<>();
        for(long i = 2; i <= 10; i++) {
            Follow follow =  Follow.builder().followingId(i).followerId(memberId).build();
            ReflectionTestUtils.setField(follow, "id", i);
            followings.add(follow);
        }
        return followings;
    }

    @Test
    @DisplayName("팔로우 저장 테스트 - 성공")
    public void saveFollow() {
        //given
        Member member1 = setUpMember();
        ReflectionTestUtils.setField(member1, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member1);


        Follow follow = setUpFollow();
        when(followRepository.findByFollowingIdAndFollowerId(1L, 2L)).thenReturn(Optional.empty());
        when(followRepository.save(ArgumentMatchers.any(Follow.class))).thenReturn(follow);

        //when
        Response response = followService.saveFollow(1L, 2L);

        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
        verify(followRepository, times(1)).save(ArgumentMatchers.any(Follow.class));

    }

    @Test
    @DisplayName("팔로우 저장 테스트 - 실패")
    public void saveFollowFail() {
        //given
        Follow follow = setUpFollow();
        when(followRepository.findByFollowingIdAndFollowerId(1L, 2L)).thenReturn(Optional.of(follow));

        //when then
        assertThrows(BadRequestException.class,() -> followService.saveFollow(1L, 2L));

    }

    @Test
    @DisplayName("팔로워 리스트 조회 테스트")
    public void findFollowList() {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        List<Follow> followers = setUpFollowersList(member.getId());
        when(followRepository.findAllByFollowingId(any(Long.class))).thenReturn(followers);

        //when
        FollowResponses followResponses = followService.findFollowList(member.getId(), FollowType.FOLLOW);

        //then
        verify(followRepository, times(1)).findAllByFollowingId(any(Long.class));
    }



    @Test
    @DisplayName("팔로잉 리스트 조회 테스트")
    public void findFollowingList() {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        List<Follow> followings = setUpFollowingList(member.getId());
        when(followRepository.findAllByFollowerId(any(Long.class))).thenReturn(followings);

        //when
        FollowResponses followResponses = followService.findFollowList(member.getId(), FollowType.FOLLOWING);

        //then
        verify(followRepository, times(1)).findAllByFollowerId(any(Long.class));
    }

    @Test
    @DisplayName("관리자가 사용자 팔로우 목록 조회 성공")
    public void getFollowSuccess(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        List<Follow> followers = setUpFollowersList(member.getId());
        when(followRepository.findAllByFollowingId(member.getId())).thenReturn(followers);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        //when
        FollowResponses responses = followService.findFollowList(member.getId(), FollowType.FOLLOW);

        //then
        assertEquals(followers.size(), responses.followResponses().size());
    }

    @Test
    @DisplayName("관리자 사용자 팔로잉 목록 조회 성공")
    public void getFollowingSuccess(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);

        List<Follow> followings = setUpFollowingList(member.getId());

        //when
        when(followRepository.findAllByFollowerId(member.getId())).thenReturn(followings);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        FollowResponses responses = followService.findFollowList(member.getId(), FollowType.FOLLOWING);

        //then
        assertEquals(followings.size(), responses.followResponses().size());
    }


}
