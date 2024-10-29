package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_JAM;

@Service
@Slf4j
@RequiredArgsConstructor
public class JamServiceImpl implements JamService {
    private final JamRepository jamRepository;
    private final EventToastRepository eventToastRepository;

    @Transactional
    @Override
    public void postJam(JamRequest jamRequest, final long eventToastId, final long memberId) {
        EventToast eventToast = eventToastRepository.findById(eventToastId);
        Jam jam = jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId);

        if (eventToast == null) {
            throw new NotFoundException(EVENT_TOAST_NOT_FOUND.getMessage());
        } else if (jam != null) {
            throw new BadRequestException(INVALID_JAM.getMessage());
        } else {
            jamRepository.save(jamRequest.toEntity(jamRequest, memberId, eventToastId));
            log.info("save jam");
        }
    }
}
