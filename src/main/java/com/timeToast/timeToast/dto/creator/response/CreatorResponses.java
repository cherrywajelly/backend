package com.timeToast.timeToast.dto.creator.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorResponses(
        List<CreatorResponse> creatorResponses
) {

}
