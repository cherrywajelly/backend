package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.util.DDayCount;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.domain.enums.fcm.FcmConstant.GIFTTOASTCREATED;
import static com.timeToast.timeToast.domain.enums.fcm.FcmConstant.GIFTTOASTOPENED;
import static com.timeToast.timeToast.global.constant.BasicImage.*;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
public class GiftToastServiceImpl implements GiftToastService{

    private final GiftToastRepository giftToastRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final ToastPieceService toastPieceService;
    private final ToastPieceRepository toastPieceRepository;
    private final ToastPieceImageRepository toastPieceImageRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final FcmService fcmService;

    public GiftToastServiceImpl(final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                final ToastPieceService toastPieceService, final ToastPieceRepository toastPieceRepository,
                                final ToastPieceImageRepository toastPieceImageRepository, final TeamRepository teamRepository,
                                final MemberRepository memberRepository, final TeamMemberRepository teamMemberRepository,
                                final IconRepository iconRepository, final FcmService fcmService) {

        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.toastPieceService = toastPieceService;
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.memberRepository = memberRepository;
        this.iconRepository = iconRepository;
        this.fcmService = fcmService;
    }


    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastGroup(final long memberId, final GiftToastGroupRequest giftToastGroupRequest) {

        iconRepository.getById(giftToastGroupRequest.iconId());
        _checkDateValidation(giftToastGroupRequest.openedDate(), giftToastGroupRequest.memorizedDate());

        if(teamMemberRepository.findByMemberIdAndTeamId(memberId, giftToastGroupRequest.teamId()).isEmpty()){
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }

        GiftToast giftToast = giftToastRepository.save(GiftToastGroupRequest.to(giftToastGroupRequest));
        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeamId(giftToastGroupRequest.teamId());

        teamMembers.forEach(
                teamMember -> giftToastOwnerRepository.save(
                        GiftToastOwner.builder()
                                .memberId(teamMember.getMemberId())
                                .giftToastId(giftToast.getId())
                                .build()));

        teamMembers.forEach(
                teamMember -> {
                    if(!teamMember.getMemberId().equals(memberId)){
                        sentCreatedMessage(teamMember.getMemberId(), giftToast);
                    }
                }
        );

        log.info("save group giftToast {} by {}", giftToast.getId(), memberId);
        return GiftToastSaveResponse.from(giftToast);
    }

    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastFriend(final long memberId, final GiftToastFriendRequest giftToastFriendRequest) {

        iconRepository.getById(giftToastFriendRequest.iconId());
        _checkDateValidation(giftToastFriendRequest.openedDate(), giftToastFriendRequest.memorizedDate());

        GiftToast giftToast = giftToastRepository.save(GiftToastFriendRequest.to(giftToastFriendRequest));

        giftToastOwnerRepository.save(
                GiftToastOwner.builder()
                        .memberId(memberId)
                        .giftToastId(giftToast.getId())
                        .build());

        giftToastOwnerRepository.save(
                GiftToastOwner.builder()
                        .memberId(giftToastFriendRequest.friendId())
                        .giftToastId(giftToast.getId())
                        .build());

        sentCreatedMessage(giftToastFriendRequest.friendId(), giftToast);

        log.info("save friend giftToast {} by {}", giftToast.getId(), memberId);
        return GiftToastSaveResponse.from(giftToast);
    }

    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastMine(final long memberId, final GiftToastMineRequest giftToastMineRequest) {

        iconRepository.getById(giftToastMineRequest.iconId());
        _checkDateValidation(giftToastMineRequest.openedDate(), giftToastMineRequest.memorizedDate());

        GiftToast giftToast = giftToastRepository.save(GiftToastMineRequest.to(giftToastMineRequest));

        giftToastOwnerRepository.save(GiftToastOwner.builder()
                .memberId(memberId)
                .giftToastId(giftToast.getId())
                .build());

        log.info("save mine giftToast {} by {}", giftToast.getId(), memberId);

        return GiftToastSaveResponse.from(giftToast);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastDetailResponse getGiftToastDetail(final long memberId, final long giftToastId) {
        GiftToast giftToast = giftToastRepository.findByGiftToastId(giftToastId)
                .orElseThrow(()-> new NotFoundException(GIFT_TOAST_NOT_FOUND.getMessage()));

        return GiftToastDetailResponse.from(
                getGiftToastInfo(memberId, giftToast),
                DDayCount.count(LocalDate.now(), giftToast.getOpenedDate()),
                getToastPieceResponses(giftToast));

    }

    @Transactional
    @Override
    public GiftToastInfo getGiftToastInfo(final long memberId, final GiftToast giftToast){

        String giftToastOwner = null;
        String profileImageUrl = BASIC_PROFILE_IMAGE_URL;

        if(giftToast.getGiftToastType().equals(GiftToastType.GROUP)){

            Optional<Team> team =  teamRepository.findById(giftToast.getTeamId());
            if(team.isPresent()){
                giftToastOwner = team.get().getName();
                profileImageUrl = team.get().getTeamProfileUrl();
            }

            return GiftToastInfo.from(giftToast, getIconImageUrl(giftToast), profileImageUrl, giftToastOwner);

        }else if(giftToast.getGiftToastType().equals(GiftToastType.FRIEND)){

            Optional<GiftToastOwner> findGiftToastOwner = giftToastOwnerRepository.findAllByGiftToastId(giftToast.getId())
                    .stream().filter(owner -> !owner.getMemberId().equals(memberId)).findFirst();

            if(findGiftToastOwner.isPresent()){
                Member member = memberRepository.getById(findGiftToastOwner.get().getMemberId());
                giftToastOwner = member.getNickname();
                profileImageUrl =  member.getMemberProfileUrl();
            }
            return GiftToastInfo.from(giftToast, getIconImageUrl(giftToast),profileImageUrl, giftToastOwner);

        }else{
            Member member = memberRepository.getById(memberId);
            return GiftToastInfo.from(giftToast, getIconImageUrl(giftToast), member.getMemberProfileUrl(), member.getNickname());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastResponses getGiftToastByMember(final long memberId) {

        List<GiftToastResponse> giftToastResponses = new ArrayList<>();

        giftToastRepository.findAllGiftToastsByMemberId(memberId).forEach(
                giftToast -> {
                    String giftToastOwner = null;

                    if(giftToast.getGiftToastType().equals(GiftToastType.GROUP)){
                        Optional<Team> findTeam = teamRepository.findById(giftToast.getTeamId());
                        if(findTeam.isPresent()){
                            giftToastOwner= findTeam.get().getName();
                        }

                    }else if(giftToast.getGiftToastType().equals(GiftToastType.FRIEND)){
                        Optional<GiftToastOwner> findGiftToastOwner = giftToastOwnerRepository.findAllByGiftToastId(giftToast.getId()).stream()
                                .filter(owner -> !owner.getMemberId().equals(memberId)).findFirst();

                        if(findGiftToastOwner.isPresent()){
                            Member findMember = memberRepository.findById(findGiftToastOwner.get().getMemberId())
                                    .orElseThrow(()-> new NotFoundException(MEMBER_NOT_FOUND.getMessage()));
                            giftToastOwner = findMember.getNickname();
                        }

                    }else{
                        giftToastOwner = memberRepository.getById(memberId).getNickname();
                    }

                    giftToastResponses.add(GiftToastResponse.from(giftToast,getIconImageUrl(giftToast), giftToastOwner));
                }
        );

        return new GiftToastResponses(giftToastResponses);
    }


    @Transactional(readOnly = true)
    @Override
    public GiftToastIncompleteResponses getGiftToastIncomplete(final long memberId) {
        List<GiftToastIncompleteResponse> giftToastIncompleteResponses = new ArrayList<>();

        giftToastRepository.findAllGiftToastsByMemberIdAndNotOpen(memberId).forEach(
                giftToast -> {
                    Optional<ToastPiece> toastPiecesByGiftToast = toastPieceRepository.
                            findAllByMemberIdAndGiftToastId(memberId,giftToast.getId()).stream().findFirst();
                    if(toastPiecesByGiftToast.isEmpty()){
                        String iconImageUrl = iconRepository.getById(giftToast.getIconId()).getIconImageUrl();
                        giftToastIncompleteResponses.add(GiftToastIncompleteResponse.from(giftToast, iconImageUrl));
                    }
                }
        );

        return new GiftToastIncompleteResponses(giftToastIncompleteResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public ToastPieceDetailResponse getToastPiece(final long memberId, final long toastPieceId) {
        ToastPiece toastPiece = toastPieceRepository.findById(toastPieceId)
                .orElseThrow(()-> new NotFoundException(TOAST_PIECE_NOT_FOUND.getMessage()));

        GiftToast giftToast = giftToastRepository.findByGiftToastId(toastPiece.getGiftToastId())
                .orElseThrow(()-> new NotFoundException(GIFT_TOAST_NOT_FOUND.getMessage()) );

        return ToastPieceDetailResponse.builder()
                .giftToastInfo(getGiftToastInfo(memberId, giftToast))
                .toastPieceResponse(toastPieceService.getToastPieceResponse(toastPiece.getId()))
                .build();
    }

    @Transactional
    @Override
    public Response deleteGiftToast(final long memberId,final long giftToastId) {

        giftToastOwnerRepository.deleteByMemberIdAndGiftToastId(memberId, giftToastId);
        if(giftToastOwnerRepository.findAllByGiftToastId(giftToastId).isEmpty()){
            List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToastId);
            toastPieces.forEach(
                    toastPiece -> {
                        toastPieceImageRepository.deleteAllByToastPieceId(toastPiece.getId());
                        toastPieceRepository.deleteToastPiece(toastPiece);
                    }
            );
            giftToastRepository.deleteById(giftToastId);
            log.info("delete giftToast {} by {}", giftToastId, memberId);
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Transactional
    @Override
    public void deleteAllGiftToast(final long memberId){
        giftToastRepository.findAllGiftToastsByMemberId(memberId).forEach(
                giftToast -> deleteGiftToast(memberId, giftToast.getId()));

    }

    private ToastPieceResponses getToastPieceResponses(final GiftToast giftToast){

        if(giftToast.getIsOpened()){
            return toastPieceService.getToastPiecesByGiftToastId(giftToast.getId());
        }
        return new ToastPieceResponses(giftToast.getId(), List.of());
    }

    private String getIconImageUrl(final GiftToast giftToast) {
        if(giftToast.getIsOpened()) {
            return iconRepository.getById(giftToast.getIconId()).getIconImageUrl();
        }
        return NOT_OPEN_IMAGE_URL;

    }

    private void _checkDateValidation(final LocalDate openedDate, final LocalDate memorizedDate){
        if((openedDate.isBefore(LocalDate.now()))){
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
    }

    private void sentCreatedMessage(final long memberId, final GiftToast giftToast) {
        fcmService.sendMessageTo(memberId,
                FcmResponse.builder()
                        .fcmConstant(GIFTTOASTCREATED)
                        .toastName(giftToast.getTitle())
                        .param(giftToast.getId())
                        .build());
    }

    private void sendOpenedMessage(final GiftToast giftToast, final long memberId) {
        fcmService.sendMessageTo(memberId,
                FcmResponse.builder()
                        .fcmConstant(GIFTTOASTOPENED)
                        .toastName(giftToast.getTitle())
                        .param(giftToast.getId())
                        .build());
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateIsOpen(){
        List<GiftToast> giftToasts = giftToastRepository.findAllGiftToastToOpen();

        giftToasts.forEach(
                giftToast -> {
                    List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(giftToast.getId());
                    List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToast.getId());

                    boolean isOpen = giftToastOwners.stream()
                            .allMatch(giftToastOwner ->
                                    toastPieces.stream().anyMatch(toastPiece -> toastPiece.getMemberId().equals(giftToastOwner.getMemberId()))
                            );

                    if(isOpen){
                        giftToast.updateIsOpened(true);
                        giftToastOwners.forEach(
                                giftToastOwner -> sendOpenedMessage(giftToast, giftToastOwner.getMemberId())

                        );
                    }

                }
        );

        log.info("update gift toast's is open");
    }

}
