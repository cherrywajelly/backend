package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.IconService;

import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

public class IconServiceTest implements IconService {


    @Override
    public IconGroupDetailResponses getToastIconGroupsByUser(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, 10);
        boolean isBuy = false;
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        List<IconGroupDetail> iconGroupDetails = List.of(new IconGroupDetail(iconGroupSummaryInfo, isBuy, iconResponses));
        return new IconGroupDetailResponses(iconGroupDetails);
    }

    @Override
    public IconGroupDetailResponses getJamIconGroupsByUser(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.JAM, 10);
        boolean isBuy = false;
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        List<IconGroupDetail> iconGroupDetails = List.of(new IconGroupDetail(iconGroupSummaryInfo, isBuy, iconResponses));
        return new IconGroupDetailResponses(iconGroupDetails);
    }

    @Override
    public IconGroupInfoResponses getAllToastsIconGroups(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, 10);
        boolean isBuy = false;

        List<IconGroupInfoResponse> iconGroupInfoResponses = List.of(new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy));

        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Override
    public IconGroupInfoResponses getAllJamsIconGroups(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.JAM, 10);
        boolean isBuy = false;

        List<IconGroupInfoResponse> iconGroupInfoResponses = List.of(new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy));

        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Override
    public IconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, 10);
        boolean isBuy = false;

        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        return new IconGroupDetail(iconGroupSummaryInfo,isBuy,iconResponses );

    }

    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}