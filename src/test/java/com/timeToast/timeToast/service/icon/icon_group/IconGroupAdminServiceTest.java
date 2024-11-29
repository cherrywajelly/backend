package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class IconGroupAdminServiceTest implements IconGroupAdminService {
    @Override
    public Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public IconGroupCreatorResponses getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponses = new ArrayList<>();
        iconGroupCreatorResponses.add(new IconGroupCreatorResponse(1, "imageUrl", "iconTitle"));
        return new IconGroupCreatorResponses(iconGroupCreatorResponses);
    }

    @Override
    public IconGroupDetailResponse getIconGroupDetail(long iconGroupId) {
        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl("thumbnailImageUrl")
                .title("title")
                .creatorNickname("nickname")
                .price(0)
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

}
