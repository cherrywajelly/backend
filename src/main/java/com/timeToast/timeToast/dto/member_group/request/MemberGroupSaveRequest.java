package com.timeToast.timeToast.dto.member_group.request;

import java.util.List;

public record MemberGroupSaveRequest(
        String groupName,
        List<Long> groupMembers
) {

}
