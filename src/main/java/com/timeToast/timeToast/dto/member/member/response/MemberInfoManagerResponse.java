package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowCaseManagerResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberInfoManagerResponse (
        long memberId,
        String memberProfileUrl,
        String nickname,
        String email,
        LoginType loginType,
        PremiumType premiumType,
        List<FollowManagerResponse> followManagerResponses,
        List<FollowingManagerResponse> followingManagerResponses,
        List<TeamDataManagerResponse> teamManagerResponses,
        List<ShowCaseManagerResponse> showCaseManagerResponses,
        List<EventToastDataManagerResponse> eventToastManagerResponses,
        List<GiftToastDataManagerResponse> giftToastManagerResponses,
        List<IconGroupManagerResponse> iconGroupManagerResponses,
        List<PaymentManagerResponse> paymentManagerResponse
) {
    public static MemberInfoManagerResponse from(final Member member, final PremiumType premiumType, final List<FollowManagerResponse> followManagerResponses,
                                                 List<FollowingManagerResponse> followingManagerResponses, List<TeamDataManagerResponse> teamManagerResponses,
                                                 List<ShowCaseManagerResponse> showCaseManagerResponses, List<EventToastDataManagerResponse> eventToastManagerResponses,
                                                 List<GiftToastDataManagerResponse> giftToastManagerResponses, List<IconGroupManagerResponse> iconGroupManagerResponses,
                                                 List<PaymentManagerResponse> paymentManagerResponse) {
        return MemberInfoManagerResponse.builder()
                .memberId(member.getId())
                .memberProfileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .loginType(member.getLoginType())
                .premiumType(premiumType)
                .followManagerResponses(followManagerResponses)
                .followingManagerResponses(followingManagerResponses)
                .teamManagerResponses(teamManagerResponses)
                .showCaseManagerResponses(showCaseManagerResponses)
                .eventToastManagerResponses(eventToastManagerResponses)
                .giftToastManagerResponses(giftToastManagerResponses)
                .iconGroupManagerResponses(iconGroupManagerResponses)
                .paymentManagerResponse(paymentManagerResponse)
                .build();
    }
}
