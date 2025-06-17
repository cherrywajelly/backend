package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.*;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorProfileResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.icon.AdminIconService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class AdminIconServiceTest implements AdminIconService {

    @Override
    public Response postIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, final long userId){

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupDetailResponse getIconGroupDetail(long iconGroupId) {
        return com.timeToast.timeToast.dto.icon.icon_group.response.admin.IconGroupDetailResponse.builder()
                .thumbnailImageUrl("thumbnailImageUrl")
                .title("title")
                .creatorNickname("nickname")
                .price(0)
                .description("description")
                .icons(List.of(new IconResponse(1L, "iconImageUrl")))
                .iconState(IconState.REGISTERED)
                .build();
    }

    @Override
    public IconGroupInfoResponse saveIconState(IconGroupStateRequest iconGroupStateRequest) {
        return IconGroupInfoResponse.builder()
                .iconGroupId(1L)
                .iconType(IconType.TOAST)
                .iconState(IconState.WAITING)
                .title("title")
                .thumbnailUrl("thumbnailUrl")
                .build();
    }

    @Override
    public IconGroupInfoResponses getIconGroupForNonApproval() {
        List<IconGroupInfoResponse> iconGroupInfoResponses = new ArrayList<>();
        iconGroupInfoResponses.add(IconGroupInfoResponse.builder()
                .iconGroupId(1L)
                .iconType(IconType.TOAST)
                .iconState(IconState.WAITING)
                .title("title")
                .thumbnailUrl("thumbnailUrl")
                .build());
        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Override
    public IconGroupAdminResponses getAllIconGroups() {
        List<IconGroupAdminResponse> iconGroupInfoResponses = new ArrayList<>();
        iconGroupInfoResponses.add(IconGroupAdminResponse.builder()
                .iconGroupId(1L)
                .title("title")
                .thumbnailUrl("thumbnailUrl")
                .iconType(IconType.TOAST)
                .iconState(IconState.WAITING)
                .nickname("nickname")
                .build());
        return new IconGroupAdminResponses(iconGroupInfoResponses);
    }

    @Override
    public CreatorIconInfos getIconGroupsByCreator(final long creatorId) {
        List<CreatorIconInfo> creatorIconInfos = new ArrayList<>();
        creatorIconInfos.add(
          CreatorIconInfo.builder()
                  .title("title")
                  .income(1000)
                  .salesCount(1)
                  .iconImageUrl(List.of("iconImageUrl"))
                  .build()
        );
        return new CreatorIconInfos(1, 1000, 10,creatorIconInfos);
    }

    @Override
    public IconGroupOverview getIconGroupOverview(final long memberId, final long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        return new IconGroupOverview(iconGroupSummaryInfo, IconState.WAITING, iconResponses, 10, 100);
    }

    @Override
    public CreatorProfileResponse getIconGroupOverviews(final long memberId) {
        IconGroupSummaryInfo iconGroupSummaryInfo = new IconGroupSummaryInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        IconGroupOverview iconGroupOverview = new IconGroupOverview(iconGroupSummaryInfo, IconState.WAITING, iconResponses, 10, 100);

        List<IconGroupOverview> iconGroupOverviews = List.of(iconGroupOverview);

        return new CreatorProfileResponse(iconGroupOverviews, 10, 10, 100, 90);
    }


    @Override
    public IconGroupMonthlyRevenues iconGroupMonthlyRevenue( final int year) {
        List<IconGroupMonthlyRevenue> iconGroupMonthlyRevenues = new ArrayList<>();
        iconGroupMonthlyRevenues.add(
                IconGroupMonthlyRevenue.builder()
                        .year(year)
                        .month(1)
                        .toastsRevenue(100L)
                        .jamsRevenue(100L)
                        .build()
        );
        return new IconGroupMonthlyRevenues(iconGroupMonthlyRevenues);
    }

}