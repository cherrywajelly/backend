package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_JAM;

@Service
@Slf4j
@RequiredArgsConstructor
public class JamServiceImpl implements JamService {
    private final JamRepository jamRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final MemberRepository memberRepository;
    private final EventToastService eventToastService;


    @Transactional
    @Override
    public void postJam(JamRequest jamRequest, final long eventToastId, final long memberId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);
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

    @Transactional
    @Override
    public List<JamResponse> getJams(final long eventToastId) {
        List<Jam> jams = jamRepository.findByEventToastId(eventToastId);
        List<JamResponse> jamResponses = new ArrayList<>();

        jams.forEach(
                jam -> {
                    Icon icon = iconRepository.getById(jam.getIconId());
                    Member member = memberRepository.getById(jam.getMemberId());
                    JamResponse jamResponse = JamResponse.fromEntity(jam.getId(), icon.getIcon_image_url(), member.getNickname());
                    jamResponses.add(jamResponse);
                }
        );

        return jamResponses;
    }
}
