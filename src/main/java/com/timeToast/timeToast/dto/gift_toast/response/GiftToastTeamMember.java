package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;

import java.util.List;

public record GiftToastTeamMember(

        int teamMembersCount,
        int isWrittenCount,
        List<MemberInfoResponse> isWrittenMembers
) {
}
