package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;

import java.util.ArrayList;
import java.util.List;

public class IconGroupAdminServiceTest implements IconGroupAdminService {
    @Override
    public void postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId){

    }

    @Override
    public List<IconGroupCreatorResponse> getIconGroupForCreator(final long memberId) {
        List<IconGroupCreatorResponse> iconGroupCreatorResponseList = new ArrayList<>();
        iconGroupCreatorResponseList.add(new IconGroupCreatorResponse(1, "imageUrl", "iconTitle"));
        return iconGroupCreatorResponseList;
    }

}
