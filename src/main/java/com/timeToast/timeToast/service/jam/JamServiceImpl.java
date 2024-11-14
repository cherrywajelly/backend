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
import com.timeToast.timeToast.service.image.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_JAM;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;

@Service
@Slf4j
@RequiredArgsConstructor
public class JamServiceImpl implements JamService {
    private final JamRepository jamRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;


    @Transactional
    @Override
    public void postJam(final JamRequest jamRequest, final MultipartFile contents, final MultipartFile image, final long eventToastId, final long memberId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Jam jam = jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId);
        System.out.println("jam : " + jam);

        if (eventToast == null) {
            throw new NotFoundException(EVENT_TOAST_NOT_FOUND.getMessage());
        } else if (jam != null) {
            throw new BadRequestException(INVALID_JAM.getMessage());
        } else {
            System.out.println("1");
            Jam newJam = jamRepository.save(jamRequest.toEntity(jamRequest, memberId, eventToastId));
            System.out.println(getContentsUrl(contents, newJam));
//            newJam.updateContentsUrl(getContentsUrl(contents, newJam));
            newJam.updateImageUrl(getContentsUrl(image, newJam));
            jamRepository.save(newJam);
            log.info("save jam");
        }
    }

    @Transactional
    public String getContentsUrl(MultipartFile contents, Jam jam) {
        String saveUrl = JAM.value() + SLASH.value() + CONTENTS.value() + SLASH.value() +  jam.getId();
        return fileUploadService.uploadfile(contents, saveUrl);
    }

    @Transactional
    public String getImageUrl(MultipartFile image, Jam jam) {
        String saveUrl = JAM.value() + SLASH.value() + IMAGE.value() + SLASH.value() +  jam.getId();
        return fileUploadService.uploadfile(image, saveUrl);
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
                    JamResponse jamResponse = JamResponse.fromEntity(jam.getId(), icon.getIconImageUrl(), member.getNickname());
                    jamResponses.add(jamResponse);
                }
        );

        return jamResponses;
    }
}
