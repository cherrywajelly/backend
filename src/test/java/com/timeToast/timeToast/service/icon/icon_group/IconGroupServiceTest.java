package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.junit.jupiter.api.Assertions.*;

public class IconGroupServiceTest implements IconGroupService {

    @Override
    public Response buyIconGroup(long memberId, long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public List<IconGroupResponses> getToastIconGroups(final long memberId) {
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        List<IconResponse> iconResponses = new ArrayList<>();
        iconResponses.add(new IconResponse(1, "iconUrl"));

        iconGroupResponses.add(new IconGroupResponses(1, "name", iconResponses));
        return iconGroupResponses;
    }

    @Override
    public List<IconGroupResponses> getJamIconGroups(final long memberId) {
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        List<IconResponse> iconResponses = new ArrayList<>();
        iconResponses.add(new IconResponse(1, "iconUrl"));

        iconGroupResponses.add(new IconGroupResponses(1, "name", iconResponses));
        return iconGroupResponses;
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
    public IconGroupDetailResponse getIconGroupDetail(final long iconGroupId) {
        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl("thumbnailImageUrl")
                .title("title")
                .creatorNickname("nickname")
                .price(0)
                .iconResponses(List.of(new IconResponse(1L, "iconImageUrl")))
                .build();
    }

    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}