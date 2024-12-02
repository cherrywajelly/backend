package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfo;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class IconGroupAdminServiceTest implements IconGroupAdminService {
    @Override
    public Response postIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, final long userId){

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public IconGroupCreatorResponses getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponses = new ArrayList<>();
        iconGroupCreatorResponses.add(new IconGroupCreatorResponse(1, "imageUrl", "iconTitle", IconState.REGISTERED));
        return new IconGroupCreatorResponses(iconGroupCreatorResponses);
    }

    @Override
    public IconGroupDetailResponse getIconGroupDetail(long iconGroupId) {
        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl("thumbnailImageUrl")
                .title("title")
                .creatorNickname("nickname")
                .price(0)
                .description("description")
                .iconResponses(List.of(new IconResponse(1L, "iconImageUrl")))
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
    public IconGroupInfoResponses getAllIconGroups() {
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
    public CreatorIconInfos getIconGroupsByCreator(final long creatorId) {
        List<CreatorIconInfo> creatorIconInfos = new ArrayList<>();
        creatorIconInfos.add(
          CreatorIconInfo.builder()
                  .title("title")
                  .revenue(1000)
                  .salesCount(1)
                  .iconImageUrl(List.of("iconImageUrl"))
                  .build()
        );
        return new CreatorIconInfos(1, 1000, 10,creatorIconInfos);
    }

    @Override
    public IconGroupCreatorDetailResponse getIconGroupDetailForCreator(final long memberId, final long iconGroupId) {
        List<String> iconImageUrls = new ArrayList<>();
        List<String> iconTitles = new ArrayList<>();
        IconGroupOrderedResponse iconGroupOrderedResponse = new IconGroupOrderedResponse("name", "thumbnailUrl", List.of("iconImageUrl"), 1, 1, IconState.REGISTERED);
        IconGroupCreatorDetailResponse iconGroupCreatorDetail = new IconGroupCreatorDetailResponse(iconGroupOrderedResponse, 1000, "description", "url", "nickname");
        return iconGroupCreatorDetail;
    }

}