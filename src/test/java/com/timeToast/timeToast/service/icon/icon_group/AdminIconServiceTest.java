package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.response.IconGroupOrderInfo;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.AdminIconService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class AdminIconServiceTest implements AdminIconService {

    @Override
    public Response postIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, final long userId){

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Override
    public IconGroupSummaryInfo saveIconState(IconGroupStateRequest iconGroupStateRequest) {
        return new IconGroupSummaryInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED, 100);
    }

    @Override
    public IconGroupOverview getIconGroupOverview(final long memberId, final long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupOrderInfo iconGroupOrderInfo = new IconGroupOrderInfo(10, 100);
        return new IconGroupOverview(iconGroupSummaryInfo, iconResponses, iconGroupOrderInfo);
    }

    @Override
    public CreatorIconGroupResponse getIconGroupsByCreator(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupOrderInfo iconGroupOrderInfo = new IconGroupOrderInfo(10, 100);

        IconGroupOverview iconGroupOverview = new IconGroupOverview(iconGroupSummaryInfo, iconResponses, iconGroupOrderInfo);

        List<IconGroupOverview> iconGroupOverviews = List.of(iconGroupOverview);

        return new CreatorIconGroupResponse(iconGroupOverviews, 10, 10, 100, 90);
    }

    @Override
    public IconGroupSummaryInfos getIconGroupForNonApproval() {
        List<IconGroupSummaryInfo> iconGroupInfoResponses = List.of(new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST, IconState.REGISTERED, 100));
        return new IconGroupSummaryInfos(iconGroupInfoResponses);
    }

    @Override
    public IconGroupDetail getIconGroupDetail(long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        return new IconGroupDetail(iconGroupSummaryInfo, iconResponses);
    }

    @Override
    public IconGroupSummaryInfos getAllIconGroups() {
        List<IconGroupSummaryInfo> iconGroupSummaryInfos = List.of(new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST,IconState.WAITING,100));

        return new IconGroupSummaryInfos(iconGroupSummaryInfos);
    }


    @Override
    public IconGroupDetailResponses getMemberIconGroupInfo(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST,IconState.WAITING,100);

        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        List<IconGroupDetail> iconGroupDetails = List.of(new IconGroupDetail(iconGroupSummaryInfo, iconResponses));
        return new IconGroupDetailResponses(iconGroupDetails);
    }



}