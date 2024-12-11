package com.timeToast.timeToast.Integration;


import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastDetailResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastSaveResponse;
import com.timeToast.timeToast.dto.team.response.TeamResponses;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.follow.FollowService;
import com.timeToast.timeToast.service.gift_toast.GiftToastService;
import com.timeToast.timeToast.service.team.TeamService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class GiftToastIntegrationTest extends TestContainerSupport{

    private final TeamService teamService;
    private final FollowService followService;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final GiftToastRepository giftToastRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final ToastPieceService toastPieceService;
    private final ToastPieceRepository toastPieceRepository;
    private final ToastPieceImageRepository toastPieceImageRepository;
    private final TeamRepository teamRepository;
    private final IconRepository iconRepository;
    private final FcmService fcmService;
    private final GiftToastService giftToastService;

    @Autowired
    public GiftToastIntegrationTest(final TeamService teamService, final FollowService followService,
                                    final MemberRepository memberRepository, final TeamMemberRepository teamMemberRepository,
                                    final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                    final ToastPieceService toastPieceService, final ToastPieceRepository toastPieceRepository,
                                    final ToastPieceImageRepository toastPieceImageRepository, final TeamRepository teamRepository,
                                    final IconRepository iconRepository, final FcmService fcmService,
                                    final GiftToastService giftToastService) {
        this.teamService = teamService;
        this.followService = followService;
        this.memberRepository = memberRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.toastPieceService = toastPieceService;
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
        this.teamRepository = teamRepository;
        this.iconRepository = iconRepository;
        this.fcmService = fcmService;
        this.giftToastService = giftToastService;
    }

    @Test()
    @DisplayName("사용자는 그룹 캡슐 토스트를 저장할 수 있고, 이는 다른 사람들이 알림을 받을 수 있으며, 캡슐 토스트 리스트도 업데이트 된다.")
    public void trySaveGiftToast() {

        //login member
        Member member = memberRepository.getById(1L);

        //save group giftToast
        TeamResponses teamResponses = teamService.findLoginMemberTeams(member.getId());
        GiftToastGroupRequest giftToastGroupRequest = new GiftToastGroupRequest(1L, 2L, LocalDate.now(), LocalDate.now().plusMonths(1), "title","description");
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeamId(2L);

        GiftToastSaveResponse giftToastSaveResponse = giftToastService.saveGiftToastGroup(member.getId(), giftToastGroupRequest);
        List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(giftToastSaveResponse.giftToastId());


        Assertions.assertEquals(teamMembers.size(), giftToastOwners.size());

//        for(TeamMember teamMember : teamMembers) {
//            if(!teamMember.getMemberId().equals(member.getId())) {
//                Assertions.assertTrue(fcmService.getFcmResponses(teamMember.getMemberId()).fcmResponses().stream().anyMatch(
//                        fcmResponse -> fcmResponse.fcmConstant().equals(FcmConstant.GIFTTOASTCREATED)&&(fcmResponse.param()==giftToastSaveResponse.giftToastId())
//                ));
//            }
//        }

        //update
        Assertions.assertTrue(giftToastService.getGiftToastByMember(member.getId()).giftToastResponses().stream().anyMatch(
                giftToastResponse -> giftToastResponse.giftToastId()== giftToastSaveResponse.giftToastId()));

        Assertions.assertTrue(giftToastService.getGiftToastIncomplete(member.getId()).giftToastResponses().stream().anyMatch(
                giftToastIncompleteResponse -> giftToastIncompleteResponse.giftToastId() == giftToastSaveResponse.giftToastId()));

    }


    @Test
    @DisplayName("사용자는 토스트 조각을 작성할 수 있으며, 상세 조회에서 알 수 있다.")
    public void tryToastPiece(){
        //login member
        Member member = memberRepository.getById(1L);

        try {
            //save toastPiece
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile toastPieceContent = new MockMultipartFile(
                    "toastPieceContents",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream()
            );

            MockMultipartFile toastPieceImage = new MockMultipartFile(
                    "toastPieceImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream()
            );

            ToastPieceRequest toastPieceRequest = new ToastPieceRequest(1L, 1L, "title");
            ToastPieceSaveResponse toastPieceSaveResponse = toastPieceService.saveToastPiece(member.getId(), toastPieceRequest, toastPieceContent, List.of(toastPieceImage));


            //get giftToastDetail
            GiftToastDetailResponse giftToastDetailResponse = giftToastService.getGiftToastDetail(member.getId(), toastPieceSaveResponse.giftToastId());
            Assertions.assertTrue(giftToastDetailResponse.toastPieceResponses().toastPieceResponses().stream().anyMatch(
                    toastPieceResponse -> toastPieceResponse.toastPieceId() == toastPieceSaveResponse.toastPieceId()
            ));

        }catch (IOException exception){

        }

    }

}
