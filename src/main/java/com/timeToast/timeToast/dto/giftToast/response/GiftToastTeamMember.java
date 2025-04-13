package com.timeToast.timeToast.dto.giftToast.response;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;

import java.util.List;

public record GiftToastTeamMember(

        int teamMembersCount,
        int isWrittenCount,
        List<MemberInfoResponse> isWrittenMembers
) {
}
