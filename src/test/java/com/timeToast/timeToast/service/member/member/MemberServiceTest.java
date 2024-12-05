package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowCaseManagerResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.exception.ConflictException;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.NICKNAME_CONFLICT;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest implements MemberService{

    @Override
    public MemberInfoResponse saveProfileImageByLogin(long memberId, MultipartFile profileImage) {
        return new MemberInfoResponse(1L, "nickname","profileUrl","email");
    }

    @Override
    public MemberInfoResponse postNickname(String nickname, long memberId) {
        if(nickname.equals("conflictNickname")){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new MemberInfoResponse(1L, "nickname","profileUrl","email");
    }

    @Override
    public Response nicknameValidation(String nickname) {
        if(nickname.equals("conflictNickname")){
            throw new ConflictException(NICKNAME_CONFLICT.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.VALID_NICKNAME.getMessage());
    }

    @Override
    public MemberInfoResponse getMemberInfo(long memberId) {

        return new MemberInfoResponse(1L,"nickname","profileUrl","email");
    }

    @Override
    public MemberProfileResponse getMemberProfileByLogin(long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl", 0,0,1, false);
    }

    @Override
    public MemberProfileResponse getMemberProfile(long loginId, long memberId) {
        return new MemberProfileResponse("nickname", "profileUrl", 0,0,1, false);
    }

    @Override
    public CreatorDetailResponse getCreatorByCreatorId(final long creatorId){
        return new CreatorDetailResponse("profileUrl", "nickname", Bank.IBK.value(),"accountNumber");
    }

    @Override
    public CreatorResponses getCreators() {
        List<CreatorResponse> creatorResponses = new ArrayList<>();
        creatorResponses.add(
                CreatorResponse.builder()
                        .memberId(1L)
                        .profileUrl("profileUrl")
                        .nickname("nickname")
                        .build()
        );
        return new CreatorResponses(creatorResponses) ;
    }

    @Override
    public MemberPremium getMemberPremium(final long memberId) {
        return new MemberPremium(1L, PremiumType.BASIC, LocalDate.now());
    }

    @Override
    public Response saveCreatorInfo(final long creatorId, final MultipartFile profile, final CreatorRequest creatorRequest) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public MemberManagerResponses getMembersForManagers() {
        List<MemberManagerResponse> memberManagerResponses = new ArrayList<>();
        memberManagerResponses.add(new MemberManagerResponse(1L, "memberProfileUrl", "nickname"));
        return new MemberManagerResponses(memberManagerResponses);
    }

    @Override
    public MemberInfoManagerResponse getMemberInfoForManager(final long memberId){
        List<FollowManagerResponse> followManagerResponses = new ArrayList<>();
        followManagerResponses.add(new FollowManagerResponse("followImage", "followNickname"));

        List<FollowingManagerResponse> followingManagerResponses = new ArrayList<>();
        followingManagerResponses.add(new FollowingManagerResponse("followingImage", "followNickname"));

        List<TeamDataManagerResponse> teamManagerResponses = new ArrayList<>();
        teamManagerResponses.add(new TeamDataManagerResponse("teamImage", "teamNickname"));

        List<ShowCaseManagerResponse> showCaseManagerResponses = new ArrayList<>();
        showCaseManagerResponses.add(new ShowCaseManagerResponse("showImage", "showNickname"));

        List<EventToastDataManagerResponse> eventToastManagerResponses = new ArrayList<>();
        eventToastManagerResponses.add(new EventToastDataManagerResponse("eventImage", "eventNickname"));

        List<GiftToastDataManagerResponse> giftToastManagerResponses = new ArrayList<>();
        giftToastManagerResponses.add(new GiftToastDataManagerResponse("giftImage", "giftNickname"));

        List<IconGroupManagerResponse> iconGroupManagerResponses = new ArrayList<>();
        iconGroupManagerResponses.add(new IconGroupManagerResponse("iconname", List.of("iconimages")));

        List<PaymentManagerResponse> paymentManagerResponses = new ArrayList<>();
        paymentManagerResponses.add(new PaymentManagerResponse(10, PaymentState.SUCCESS, "orderId", ItemType.ICON, "itemData", LocalDate.of(2024, 1, 1), "nickname", List.of("images"), LocalDate.of(2024,1,1)));

        return new MemberInfoManagerResponse(1L, "memberProfileUrl", "nickname", "email", LoginType.GOOGLE, PremiumType.PREMIUM,
                followManagerResponses, followingManagerResponses, teamManagerResponses, showCaseManagerResponses, eventToastManagerResponses, giftToastManagerResponses, iconGroupManagerResponses, paymentManagerResponses);
    }
}