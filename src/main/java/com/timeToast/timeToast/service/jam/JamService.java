package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JamService {
    Response postJam(JamRequest jamRequest, MultipartFile contents, MultipartFile image, final long eventToastId, final long memberId);

    JamResponses getJams(final long eventToastId);

    JamDetailResponse getJam(final long memberId, final long jamId);

    Response deleteJam(final long memberId, final long jamId);
}
