package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.dto.jam.request.JamRequest;

public interface JamService {
    void postJam(JamRequest jamRequest, final long eventToastId, final long memberId);
}
