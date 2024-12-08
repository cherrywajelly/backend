package com.timeToast.timeToast.dto.team.request;

import java.util.List;

public record TeamSaveRequest(
        String teamName,
        List<Long> teamMembers
) {

}
