package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JamService {
    void postJam(JamRequest jamRequest, MultipartFile contents, MultipartFile image, final long eventToastId, final long memberId);

    List<JamResponse> getJams(final long eventToastId);
}
