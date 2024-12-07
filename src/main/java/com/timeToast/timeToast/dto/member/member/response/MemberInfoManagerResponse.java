package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowManagerResponse;
import com.timeToast.timeToast.dto.follow.response.FollowingManagerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDataManagerResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupManagerResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamDataManagerResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentManagerResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseManagerResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberInfoManagerResponse (
        long memberId,
        String memberProfileUrl,
        String nickname,
        String email,
        MemberRole memberRole,
        LoginType loginType,
        PremiumType premiumType
) {
    public static MemberInfoManagerResponse from(final Member member, final PremiumType premiumType) {
        return MemberInfoManagerResponse.builder()
                .memberId(member.getId())
                .memberProfileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .memberRole(member.getMemberRole())
                .loginType(member.getLoginType())
                .premiumType(premiumType)
                .build();
    }
}
