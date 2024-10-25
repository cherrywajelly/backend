package com.timeToast.timeToast.dto.member_group.request;

import java.util.List;

public record TeamSaveRequest(
        String teamName,
        List<Long> teamMembers
) {

}
