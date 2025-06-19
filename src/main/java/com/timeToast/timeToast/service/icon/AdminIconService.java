package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminIconService {
    Response postIconGroup(final MultipartFile thumbnailIcon, final List<MultipartFile> files,
                           final IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupSummaryInfo saveIconState(final IconGroupStateRequest iconGroupStateRequest);
    IconGroupOverview getIconGroupOverview(final long memberId, final long iconGroupId);
    CreatorIconGroupResponse getIconGroupsByCreator(final long memberId);
    IconGroupDetail getIconGroupDetail(final long iconGroupId);
    IconGroupDetailResponses getMemberIconGroupInfo(final long memberId);
    IconGroupSummaryInfos getIconGroupForNonApproval();
    IconGroupSummaryInfos getAllIconGroups();

}
