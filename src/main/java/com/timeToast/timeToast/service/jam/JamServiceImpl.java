package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastDataResponse;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamDataResponse;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.*;
import static com.timeToast.timeToast.global.constant.FileConstant.SLASH;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class JamServiceImpl implements JamService {
    private final JamRepository jamRepository;
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;
    private final FcmService fcmService;

    @Value("${spring.cloud.oci.base-url}")
    private String baseUrl;


    @Transactional
    @Override
    public Response postJam(final JamRequest jamRequest, final MultipartFile contents, final MultipartFile image, final long eventToastId, final long memberId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);

        if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId()).isPresent()) {
             throw new BadRequestException(INVALID_NEW_JAM.getMessage());
        }


        Jam newJam = jamRepository.save(jamRequest.toEntity(jamRequest, memberId, eventToastId));
        newJam.updateContentsUrl(getContentsUrl(contents, newJam));
        if(image!=null){
            newJam.updateImageUrl(getImageUrl(image, newJam));
            jamRepository.save(newJam);
        }
        log.info("save jam");

        //알림
        Member jamMember = memberRepository.getById(memberId);
        fcmService.sendMessageTo(eventToast.getMemberId(), new FcmPostRequest(FcmConstant.EVENTTOASTSPREAD, jamMember.getNickname(), eventToast.getTitle(), eventToastId));

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional(readOnly = true)
    public String getContentsUrl(final MultipartFile contents, final Jam jam) {
        String saveUrl = baseUrl + JAM.value() + SLASH.value() + CONTENTS.value() + SLASH.value() +  jam.getId();
        return fileUploadService.uploadfile(contents, saveUrl);
    }

    @Transactional(readOnly = true)
    public String getImageUrl(final MultipartFile image, final Jam jam) {
        String saveUrl = baseUrl + JAM.value() + SLASH.value() + IMAGE.value() + SLASH.value() +  jam.getId();
        return fileUploadService.uploadfile(image, saveUrl);
    }

    @Transactional(readOnly = true)
    @Override
    public JamResponses getJams(final long eventToastId) {
        List<Jam> jams = jamRepository.findAllByEventToastId(eventToastId);
        List<JamResponse> jamResponses = new ArrayList<>();

        jams.forEach(
                jam -> {
                    Icon icon = iconRepository.getById(jam.getIconId());
                    Member member = memberRepository.getById(jam.getMemberId());
                    JamResponse jamResponse = JamResponse.from(jam.getId(), icon.getIconImageUrl(), member.getNickname());
                    jamResponses.add(jamResponse);
                }
        );
        return new JamResponses(jamResponses);
    }


    @Transactional(readOnly = true)
    @Override
    public JamDetailResponse getJam(final long memberId, final long jamId){
        Jam jam = jamRepository.getById(jamId);
        EventToast eventToast = eventToastRepository.getById(jam.getEventToastId());

        if(!eventToast.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_JAM.getMessage());
        }

        Member eventToastMember = memberRepository.getById(eventToast.getMemberId());
        Member jamMember = memberRepository.getById(jam.getMemberId());

        Icon eventToastIcon = iconRepository.getById(eventToast.getIconId());
        Icon jamIcon = iconRepository.getById(jam.getIconId());

        EventToastDataResponse eventToastDataResponse = EventToastDataResponse.fromEntity(eventToast, eventToastMember.getNickname(), eventToastMember.getMemberProfileUrl(), eventToastIcon.getIconImageUrl());
        JamDataResponse jamDataResponse = JamDataResponse.fromEntity(jam, jamIcon.getIconImageUrl(), jamMember.getMemberProfileUrl(), jamMember.getNickname());

        return new JamDetailResponse(eventToastDataResponse, jamDataResponse);

    }

    @Transactional
    @Override
    public Response deleteJam(final long memberId, final long jamId){
        Jam jam = jamRepository.getById(jamId);
        EventToast eventToast = eventToastRepository.getById(jam.getEventToastId());

        if(!eventToast.getMemberId().equals(memberId)){
            throw new BadRequestException(INVALID_JAM.getMessage());
        }
        jamRepository.deleteById(jam.getId());
        log.info("delete jam");
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }


}
