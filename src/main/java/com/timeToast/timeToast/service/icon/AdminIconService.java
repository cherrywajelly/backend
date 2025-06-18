package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOverview;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorProfileResponse;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminIconService {
    Response postIconGroup(final MultipartFile thumbnailIcon, final List<MultipartFile> files,
                           final IconGroupPostRequest iconGroupPostRequest, final long userId);

    IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest);

    IconGroupOverview getIconGroupOverview(final long memberId, final long iconGroupId);

    CreatorProfileResponse getIconGroupOverviews(final long memberId);

    IconGroupDetailResponse getIconGroupDetail(final long iconGroupId);

    IconGroupInfoResponses getIconGroupForNonApproval();

    CreatorIconInfos getIconGroupsByCreator(final long creatorId);

    IconGroupAdminResponses getAllIconGroups();

}
