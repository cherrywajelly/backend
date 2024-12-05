package com.timeToast.timeToast.dto.member.member.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberItemDataResponse (
        String itemTypeData,
        List<String> images
){
}
