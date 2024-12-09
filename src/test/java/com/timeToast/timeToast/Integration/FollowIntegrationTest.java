package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.dto.follow.response.FollowResponses;
import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.follow.FollowService;
import com.timeToast.timeToast.service.search.SearchService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class FollowIntegrationTest extends TestContainerSupport {

    private final MemberRepository memberRepository;
    private final FollowService followService;
    private final SearchService searchService;
    private final FcmService fcmService;

    @Autowired
    public FollowIntegrationTest(final MemberRepository memberRepository, final FollowService followService,
                                 final SearchService searchService, final FcmService fcmService) {
        this.memberRepository = memberRepository;
        this.followService = followService;
        this.searchService = searchService;
        this.fcmService = fcmService;
    }



    @Test
    @DisplayName("사용자는 사용자 검색을 한 결과로 팔로우를 할 수 있으며, 팔로잉 계정은 알림을 받을 수 있다.")
    public void tryFollowAndGetNotification() {

        //login member
        Member member = memberRepository.getById(1L);
        FollowResponses beforeFollowFollowingList = followService.findFollowingList(member.getId());

        Member followedMember = memberRepository.save(Member.builder()
                        .nickname("newFollowMember")
                        .email("email@email.com")
                        .memberProfileUrl("memberProfileUrl")
                        .build());
        //search
        SearchRequest searchRequest = new SearchRequest(0, 10, "newFollowMember");
        SearchResponses searchResponses = searchService.searchNickname(searchRequest);
        Assertions.assertTrue(searchResponses.searchResponses().size()< searchRequest.size());


        //save follow
        Member followMember = memberRepository.getById(searchResponses.searchResponses().get(0).memberId());
        Response response = followService.saveFollow(followMember.getId(),member.getId());
        FollowResponses afterFollowFollowingList = followService.findFollowingList(member.getId());

        Assertions.assertEquals(StatusCode.OK.getStatusCode(),response.statusCode());
        Assertions.assertEquals(beforeFollowFollowingList.followResponses().size()+1, afterFollowFollowingList.followResponses().size());

        //followMember fcm update
        FcmResponses afterFcmResponses = fcmService.getFcmResponses(followMember.getId());
        Assertions.assertTrue(afterFcmResponses.fcmResponses().stream().anyMatch(
                fcmResponse -> fcmResponse.nickname().equals(member.getNickname()) && fcmResponse.fcmConstant().equals(FcmConstant.FOLLOW)));
    }


    @Test
    @DisplayName("사용자는 팔로우를 취소할 수 있다.")
    public void tryDeleteFollowings() {

        //login member
        Member member = memberRepository.getById(1L);
        FollowResponses beforeFollowFollowingList = followService.findFollowingList(member.getId());

        //follow user
        Member followMember = memberRepository.getById(beforeFollowFollowingList.followResponses().get(0).memberId());
        FollowResponses beforeFollowerList = followService.findFollowerList(followMember.getId());


        //delete follow
        Response response = followService.deleteFollowing(followMember.getId(),member.getId());
        FollowResponses afterFollowFollowingList = followService.findFollowingList(member.getId());
        FollowResponses afterFollowerList = followService.findFollowerList(followMember.getId());


        Assertions.assertEquals(StatusCode.OK.getStatusCode(),response.statusCode());
        Assertions.assertFalse(afterFollowFollowingList.followResponses().stream().anyMatch(followResponse -> followResponse.memberId()==followMember.getId()));
        Assertions.assertEquals(beforeFollowerList.followResponses().size()-1, afterFollowerList.followResponses().size());


    }



}

