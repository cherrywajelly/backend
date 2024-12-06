package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponses;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponses;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.*;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponses;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponses;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberInfoResponse saveProfileImageByLogin(final long memberId, final MultipartFile profileImage);
    MemberInfoResponse postNickname(final String nickname, final long memberId);
    Response nicknameValidation(final String nickname);
    MemberInfoResponse getMemberInfo(final long memberId);
    MemberProfileResponse getMemberProfileByLogin(final long memberId);
    MemberProfileResponse getMemberProfile(final long loginId, final long memberId);
    CreatorResponses getCreators();
    CreatorDetailResponse getCreatorByCreatorId(final long creatorId);
    MemberPremium getMemberPremium(final long memberId);
    Response saveCreatorInfo(final long creatorId, final MultipartFile profile, final CreatorRequest creatorRequest);
//    void getCreatorMonthSettlement(final int year, final int month);
//    getCreatorMonthSettlementByCreatorId();
}
