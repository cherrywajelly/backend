package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.response.IconGroupOrderInfo;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.CreatorIconGroupResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AdminIconServiceTest implements AdminIconService {

    @Override
    public IconGroupInfo postIconGroup(MultipartFile thumbnailIcon, List<MultipartFile> files, IconGroupPostRequest iconGroupPostRequest, final long userId){

        return new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, IconState.WAITING,100);
    }

    @Override
    public IconGroupInfo saveIconState(IconGroupStateRequest iconGroupStateRequest) {
        return new IconGroupInfo(1L, "title", "creatorNickname",
                "thumbnailImageUrl", "description", IconType.TOAST, IconState.REGISTERED, 100);
    }

    @Override
    public CreatorIconGroup getCreatorIconGroup(final long memberId, final long iconGroupId) {
        IconGroupInfo iconGroupInfo = new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST, IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupOrderInfo iconGroupOrderInfo = new IconGroupOrderInfo(10, 100);
        return new CreatorIconGroup(iconGroupInfo, iconResponses, iconGroupOrderInfo);
    }

    @Override
    public CreatorIconGroupResponse getCreatorIconGroups(final long memberId) {
        IconGroupInfo iconGroupInfo = new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        IconGroupOrderInfo iconGroupOrderInfo = new IconGroupOrderInfo(10, 100);

        CreatorIconGroup creatorIconGroup = new CreatorIconGroup(iconGroupInfo, iconResponses, iconGroupOrderInfo);

        List<CreatorIconGroup> creatorIconGroups = List.of(creatorIconGroup);

        return new CreatorIconGroupResponse(creatorIconGroups, 10, 10, 100, 90);
    }

    @Override
    public IconGroupInfos getIconGroupForNonApproval() {
        List<IconGroupInfo> iconGroupInfoResponses = List.of(new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST, IconState.REGISTERED, 100));
        return new IconGroupInfos(iconGroupInfoResponses);
    }

    @Override
    public IconGroupDetail getIconGroupDetail(long iconGroupId) {
        IconGroupInfo iconGroupInfo = new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description", IconType.TOAST,IconState.WAITING,100);
        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));
        return new IconGroupDetail(iconGroupInfo, iconResponses);
    }

    @Override
    public IconGroupInfos getAllIconGroups() {
        List<IconGroupInfo> iconGroupInfos = List.of(new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST,IconState.WAITING,100));

        return new IconGroupInfos(iconGroupInfos);
    }


    @Override
    public IconGroupDetailResponses getMemberIconGroupInfo(final long memberId) {
        IconGroupInfo iconGroupInfo = new IconGroupInfo(1L, "title",
                "creatorNickname", "thumbnailImageUrl", "description",
                IconType.TOAST,IconState.WAITING,100);

        List<IconResponse> iconResponses = List.of(new IconResponse(1L, "iconImageUrl"));

        List<IconGroupDetail> iconGroupDetails = List.of(new IconGroupDetail(iconGroupInfo, iconResponses));
        return new IconGroupDetailResponses(iconGroupDetails);
    }



}