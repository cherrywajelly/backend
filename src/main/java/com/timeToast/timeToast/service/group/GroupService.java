package com.timeToast.timeToast.service.group;

import com.timeToast.timeToast.dto.group.request.GroupSaveRequest;
import com.timeToast.timeToast.dto.group.response.GroupResponse;
import com.timeToast.timeToast.dto.group.response.GroupResponses;
import org.springframework.web.multipart.MultipartFile;

public interface GroupService {

    GroupResponse saveGroup(final long memberId, final GroupSaveRequest groupSaveRequest);

    GroupResponse saveGroupImage(final long groupId, final MultipartFile multipartFile);

    GroupResponses findLoginMemberGroups(final long memberId);

    void deleteGroup(final long memberId, final long groupId);

}
