package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminIconService {
    IconGroupInfo postIconGroup(final MultipartFile thumbnailIcon, final List<MultipartFile> files,
                                final IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupInfo saveIconState(final IconGroupStateRequest iconGroupStateRequest);
    CreatorIconGroup getCreatorIconGroup(final long memberId, final long iconGroupId);
    CreatorIconGroupResponse getCreatorIconGroups(final long memberId);
    IconGroupDetail getIconGroupDetail(final long iconGroupId);
    IconGroupDetailResponses getMemberIconGroupInfo(final long memberId);
    IconGroupInfos getIconGroupForNonApproval();
    IconGroupInfos getAllIconGroups();

}
