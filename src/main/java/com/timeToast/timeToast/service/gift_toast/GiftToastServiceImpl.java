package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.GIFT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_GIFT_TOAST;

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

    public GiftToastServiceImpl(final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                final ToastPieceImageRepository toastPieceImageRepository,
                                final ToastPieceService toastPieceService, final ToastPieceRepository toastPieceRepository,
                                final TeamRepository teamRepository, final TeamMemberRepository teamMemberRepository,
                                final MemberRepository memberRepository,final IconRepository iconRepository) {
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.toastPieceService = toastPieceService;
        this.toastPieceRepository = toastPieceRepository;
        this.toastPieceImageRepository = toastPieceImageRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.memberRepository = memberRepository;
        this.iconRepository = iconRepository;
    }


    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastGroup(long memberId, GiftToastGroupRequest giftToastGroupRequest) {

        iconRepository.getById(giftToastGroupRequest.iconId());
        _checkDateValidation(giftToastGroupRequest.openedDate(), giftToastGroupRequest.memorizedDate());

        GiftToast giftToast = giftToastRepository.save(GiftToastGroupRequest.to(giftToastGroupRequest));

        List<TeamMember> teamMembers = teamMemberRepository.findAllByTeamId(giftToastGroupRequest.teamId());

        teamMembers.forEach(
                teamMember -> giftToastOwnerRepository.save(
                        GiftToastOwner
                                        .builder()
                                        .memberId(teamMember.getMemberId())
                                        .giftToastId(giftToast.getId())
                                        .build()));

        return GiftToastSaveResponse.from(giftToast);
    }

    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastFriend(long memberId, GiftToastFriendRequest giftToastFriendRequest) {

        iconRepository.getById(giftToastFriendRequest.iconId());
        _checkDateValidation(giftToastFriendRequest.openedDate(), giftToastFriendRequest.memorizedDate());

        GiftToast giftToast = giftToastRepository.save(GiftToastFriendRequest.to(giftToastFriendRequest));

        giftToastOwnerRepository.save(
                GiftToastOwner
                        .builder()
                        .memberId(memberId)
                        .giftToastId(giftToast.getId())
                        .build());

        giftToastOwnerRepository.save(
                GiftToastOwner
                        .builder()
                        .memberId(giftToastFriendRequest.friendId())
                        .giftToastId(giftToast.getId())
                        .build());

        return GiftToastSaveResponse.from(giftToast);
    }

    @Transactional
    @Override
    public GiftToastSaveResponse saveGiftToastMine(long memberId, GiftToastMineRequest giftToastMineRequest) {

        iconRepository.getById(giftToastMineRequest.iconId());
        _checkDateValidation(giftToastMineRequest.openedDate(), giftToastMineRequest.memorizedDate());

        GiftToast giftToast = giftToastRepository.save(GiftToastMineRequest.to(giftToastMineRequest));

        giftToastOwnerRepository.save(GiftToastOwner
                .builder()
                .memberId(memberId)
                .giftToastId(giftToast.getId())
                .build());

        return GiftToastSaveResponse.from(giftToast);
    }

    @Override
    public GiftToastDetailResponse getGiftToast(final long memberId, final long giftToastId) {
        GiftToast giftToast = giftToastRepository.findByGiftToastId(giftToastId).orElseThrow(()-> new NotFoundException(GIFT_TOAST_NOT_FOUND.getMessage()));

        String giftToastOwner;

        if(giftToast.getGiftToastType() == GiftToastType.GROUP){
            giftToastOwner= teamRepository.findById(giftToast.getTeamId()).get().getName();
        }else if(giftToast.getGiftToastType() == GiftToastType.FRIEND){
            Optional<GiftToastOwner> findGiftToastOwner = giftToastOwnerRepository.findAllByGiftToastId(giftToastId).stream().filter(owner -> !owner.getMemberId().equals(memberId)).findFirst();
            giftToastOwner = memberRepository.findById(findGiftToastOwner.get().getMemberId()).orElseGet(null).getNickname();
        }else{
            giftToastOwner = memberRepository.getById(memberId).getNickname();
        }

        ToastPieceResponses toastPieceResponses = toastPieceService.getToastPiecesByGiftToastId(giftToastId);

        if(!giftToast.getIsOpened()){
            _updateIsOpened(giftToast);
        }

        String iconImageUrl = iconRepository.getById(giftToast.getIconId()).getIcon_image_url();

        return GiftToastDetailResponse.from(giftToast,iconImageUrl,giftToastOwner,toastPieceResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastResponses getGiftToastByMember(final long memberId) {
        List<GiftToast> giftToasts = giftToastRepository.getGiftToastByMemberId(memberId);
        List<GiftToastResponse> giftToastResponses = new ArrayList<>();

        giftToasts.forEach(
                giftToast -> {
                    String giftToastOwner;
                    if(giftToast.getGiftToastType() == GiftToastType.GROUP){
                        giftToastOwner= teamRepository.findById(giftToast.getTeamId()).get().getName();
                    }else if(giftToast.getGiftToastType() == GiftToastType.FRIEND){
                        List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(giftToast.getId());
                        Optional<GiftToastOwner> findGiftToastOwner = giftToastOwners.stream().filter(owner -> !owner.getMemberId().equals(memberId)).findFirst();

                        giftToastOwner = memberRepository.findById(findGiftToastOwner.get().getMemberId()).orElseGet(null).getNickname();

                    }else{
                        giftToastOwner = memberRepository.getById(memberId).getNickname();
                    }


                    if(!giftToast.getIsOpened()){
                        _updateIsOpened(giftToast);
                    }

                    String iconImageUrl = iconRepository.getToastIconById(giftToast.getIconId(), giftToast.getIsOpened()).getIcon_image_url();

                    giftToastResponses.add(GiftToastResponse.from(giftToast,iconImageUrl, giftToastOwner));
                }
        );

        return new GiftToastResponses(giftToastResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastIncompleteResponses getGiftToastIncomplete(final long memberId) {
        List<GiftToast> giftToasts = giftToastRepository.getGiftToastByMemberId(memberId);
        List<GiftToastIncompleteResponse> giftToastIncompleteResponses = new ArrayList<>();

        giftToasts.forEach(
                giftToast -> {
                    Optional<ToastPiece> toastPiecesByGiftToast = toastPieceRepository.findAllByMemberIdAndGiftToastId(memberId,giftToast.getId()).stream().findFirst();

                    if(toastPiecesByGiftToast.isEmpty()){
                        String iconImageUrl = iconRepository.getById(giftToast.getIconId()).getIcon_image_url();
                        giftToastIncompleteResponses.add(GiftToastIncompleteResponse.from(giftToast, iconImageUrl));
                    }
                }
        );

        return new GiftToastIncompleteResponses(giftToastIncompleteResponses);
    }

    @Transactional
    @Override
    public void deleteGiftToast(final long memberId,final long giftToastId) {

        giftToastOwnerRepository.deleteByMemberIdAndGiftToastId(memberId, giftToastId);
        List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(giftToastId);
        if(giftToastOwners.isEmpty()){
            //giftToast

            List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToastId);
            toastPieces.forEach(
                    toastPiece -> {
                        toastPieceImageRepository.deleteAllByToastPieceId(toastPiece.getId());
                        toastPieceService.deleteToastPiece(toastPiece);
                    }
            );
            //giftToast
            giftToastRepository.deleteById(giftToastId);
        }
    }


    private void _checkDateValidation(final LocalDate openedDate, final LocalDate memorizedDate){
        if(openedDate.isBefore(LocalDate.now()) || memorizedDate.isAfter(LocalDate.now())){
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
    }
    private void _updateIsOpened(final GiftToast giftToast){
        if(giftToast.getOpenedDate().isBefore(LocalDate.now()) && giftToastOwnerRepository.checkAllGiftToastOwnerWrote(giftToast.getId())){
            giftToast.updateIsOpened(true);
        }
    }




}
