package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.orders.Orders;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetail;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.transaction.annotation.Transactional;

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
    public IconGroupCreatorDetail getIconGroupDetailForCreator(final long memberId, final long iconGroupId) {
        List<String> iconImageUrls = new ArrayList<>();
        IconGroupCreatorDetail iconGroupCreatorDetail = new IconGroupCreatorDetail("name", 1100, "description", iconImageUrls, "creatorUrl", "nickname", 0, 0);
        return iconGroupCreatorDetail;
    }

}