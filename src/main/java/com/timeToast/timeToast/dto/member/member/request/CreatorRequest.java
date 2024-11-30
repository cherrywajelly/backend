package com.timeToast.timeToast.dto.member.member.request;

import com.timeToast.timeToast.dto.creator_account.response.CreatorAccountResponse;

public record CreatorRequest(
        String nickname,
        CreatorAccountResponse creatorAccountResponse
){
}
