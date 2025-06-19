package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import lombok.Builder;

//TODO
@Builder
public record CreatorResponse(
        CreatorInfoResponse creatorInfo,
        CreatorIconGroupResponse creatorIconGroupResponse
) {

}
