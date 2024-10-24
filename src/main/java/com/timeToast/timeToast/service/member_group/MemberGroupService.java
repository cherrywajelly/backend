package com.timeToast.timeToast.service.member_group;

import com.timeToast.timeToast.dto.member_group.request.MemberGroupSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponse;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponses;
import org.springframework.web.multipart.MultipartFile;

public interface MemberGroupService {

    MemberGroupResponse saveGroup(final long memberId, final MemberGroupSaveRequest groupSaveRequest);

    MemberGroupResponse saveGroupImage(final long groupId, final MultipartFile multipartFile);

    MemberGroupResponses findLoginMemberGroups(final long memberId);

    void deleteMemberGroup(final long memberId, final long groupId);

}
