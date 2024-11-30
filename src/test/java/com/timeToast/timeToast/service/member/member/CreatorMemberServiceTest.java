package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public class CreatorMemberServiceTest implements CreatorService {

    @Override
    public CreatorInfoResponse putCreatorInfo(final long memberId, MultipartFile multipartFile, CreatorRequest creatorRequest){
        CreatorInfoResponse creatorInfoResponse = new CreatorInfoResponse(creatorRequest.nickname(), Bank.CZNBKRSE, "1234", "profileUrl");
        return creatorInfoResponse;
    }

    @Override
    public CreatorProfileResponse getCreatorProfile(final long memberId){
        CreatorInfoResponse creatorInfoResponse = new CreatorInfoResponse("nickname", Bank.CZNBKRSE, "1234", "profileUrl");
        List<IconGroupOrderedResponse> iconGroupOrderedResponses = new ArrayList<>();
        iconGroupOrderedResponses.add(new IconGroupOrderedResponse("iconName", List.of("name", "name2"), 1, 1100));
        CreatorProfileResponse creatorProfileResponse = new CreatorProfileResponse(creatorInfoResponse, new IconGroupOrderedResponses(iconGroupOrderedResponses), 100, 100, 100);
        return creatorProfileResponse;
    }
}
