package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class IconGroupServiceTest implements IconGroupService {


    @Override
    public IconGroupResponses getToastIconGroups(final long memberId) {
        List<IconGroupResponse> iconGroupResponses = new ArrayList<>();

        List<IconResponse> iconResponses = new ArrayList<>();
        iconResponses.add(new IconResponse(1, "iconUrl"));

        iconGroupResponses.add(new IconGroupResponse(1, "name", iconResponses));
        return new IconGroupResponses(iconGroupResponses);
    }

    @Override
    public IconGroupResponses getJamIconGroups(final long memberId) {
        List<IconGroupResponse> iconGroupResponses = new ArrayList<>();

        List<IconResponse> iconResponses = new ArrayList<>();
        iconResponses.add(new IconResponse(1, "iconUrl"));

        iconGroupResponses.add(new IconGroupResponse(1, "name", iconResponses));
        return new IconGroupResponses(iconGroupResponses);
    }

    @Override
    public IconGroupMarketResponses getAllToastsIconGroups(final long memberId) {
        List<IconGroupMarketResponse> iconGroupMarketResponses = new ArrayList<>();

        iconGroupMarketResponses.add(
                IconGroupMarketResponse.builder()
                        .iconGroupId(1L)
                        .title("title")
                        .thumbnailImageUrl("thumbnailImage")
                        .creatorNickname("nickname")
                        .iconType(IconType.TOAST)
                        .isBuy(false).build());

        return new IconGroupMarketResponses(iconGroupMarketResponses);
    }

    @Override
    public IconGroupMarketResponses getAllJamsIconGroups(final long memberId) {
        List<IconGroupMarketResponse> iconGroupMarketResponses = new ArrayList<>();

        iconGroupMarketResponses.add(
                IconGroupMarketResponse.builder()
                        .iconGroupId(1L)
                        .title("title")
                        .thumbnailImageUrl("thumbnailImage")
                        .creatorNickname("nickname")
                        .iconType(IconType.JAM)
                        .isBuy(false).build());

        return new IconGroupMarketResponses(iconGroupMarketResponses);
    }

    @Override
    public IconGroupMarketDetailResponse getIconGroupDetail(final long memberId, final long iconGroupId) {
        return IconGroupMarketDetailResponse.builder()
                .thumbnailImageUrl("thumbnailImageUrl")
                .title("title")
                .creatorNickname("nickname")
                .price(0)
                .iconResponses(List.of(new IconResponse(1L, "iconImageUrl")))
                .iconState(IconState.REGISTERED)
                .isBuy(false)
                .build();
    }

    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}