package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import org.springframework.web.multipart.MultipartFile;

public interface CreatorService {
    CreatorInfoResponse putCreatorInfo(final long memberId, MultipartFile multipartFile, CreatorRequest creatorRequest);
}
