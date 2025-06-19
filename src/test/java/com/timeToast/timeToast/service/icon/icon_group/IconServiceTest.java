package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.IconService;

import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

public class IconServiceTest implements IconService {


    @Override
    public UserIconGroupDetailResponses getToastIconGroupsByUser(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED,10);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupDetail iconGroupDetail = new IconGroupDetail(iconGroupSummaryInfo, iconResponses);
        boolean isBuy = false;

        List<UserIconGroupDetail> userIconGroupDetails = List.of(new UserIconGroupDetail(iconGroupDetail, isBuy));
        return new UserIconGroupDetailResponses(userIconGroupDetails);
    }

    @Override
    public UserIconGroupDetailResponses getJamIconGroupsByUser(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED,10);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupDetail iconGroupDetail = new IconGroupDetail(iconGroupSummaryInfo, iconResponses);
        boolean isBuy = false;

        List<UserIconGroupDetail> userIconGroupDetails = List.of(new UserIconGroupDetail(iconGroupDetail, isBuy));
        return new UserIconGroupDetailResponses(userIconGroupDetails);
    }

    @Override
    public IconGroupInfoResponses getAllToastsIconGroups(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED,10);
        boolean isBuy = false;

        List<IconGroupInfoResponse> iconGroupInfoResponses = List.of(new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy));

        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Override
    public IconGroupInfoResponses getAllJamsIconGroups(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.JAM, IconState.REGISTERED,10);
        boolean isBuy = false;

        List<IconGroupInfoResponse> iconGroupInfoResponses = List.of(new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy));

        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Override
    public UserIconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED,10);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupDetail iconGroupDetail = new IconGroupDetail(iconGroupSummaryInfo, iconResponses);

        boolean isBuy = false;

        return new UserIconGroupDetail(iconGroupDetail,isBuy);

    }

    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}