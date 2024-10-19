package com.timeToast.timeToast.dto.group.request;

import lombok.Getter;

import java.util.List;

public record GroupSaveRequest(
        String groupName,
        List<Long> groupMembers
) {

}
