package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
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

}
