package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.member_group.group_member.GroupMemberRepository;
import com.timeToast.timeToast.repository.member_group.member_group.MemberGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_GIFT_TOAST;

@Service
@Slf4j
public class GiftToastServiceImpl implements GiftToastService{

    private final ToastPieceService toastPieceService;
    private final GiftToastRepository giftToastRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final MemberGroupRepository groupRepository;
    private final ToastPieceRepository toastPieceRepository;
    private final GroupMemberRepository memberGroupRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;

    public GiftToastServiceImpl(final ToastPieceService toastPieceService,
                                final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                final MemberGroupRepository groupRepository, final ToastPieceRepository toastPieceRepository,
                                final GroupMemberRepository memberGroupRepository, final MemberRepository memberRepository,
                                final IconRepository iconRepository) {
        this.toastPieceService = toastPieceService;
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.groupRepository = groupRepository;
        this.toastPieceRepository = toastPieceRepository;
        this.memberGroupRepository = memberGroupRepository;
        this.memberRepository = memberRepository;
        this.iconRepository = iconRepository;
    }


    @Transactional
    @Override
    public GiftToastResponse saveGiftToast(long memberId, GiftToastRequest giftToastRequest) {
        List<GiftToastOwnerResponse> giftToastOwnerResponses = new ArrayList<>();
        String groupName = null;

        //TODO 아이콘 확인

        //giftToast save
        GiftToast giftToast = giftToastRepository.save(GiftToastRequest.to(giftToastRequest));

        //giftToastOwner save
        if(giftToastRequest.giftToastType() == GiftToastType.GROUP){
            giftToastOwnerResponses = saveGroupGiftToast(giftToast.getId(),giftToastRequest);
            groupName = groupRepository.getById(giftToastRequest.groupId()).getName();

        }else if (giftToastRequest.giftToastType() == GiftToastType.FRIEND) {
            if(!giftToastRequest.giftToastMembers().contains(memberId) && giftToastRequest.giftToastMembers().size()!=2){
                throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
            }
            giftToastOwnerResponses = saveFriendGiftToast(giftToast.getId(),giftToastRequest.giftToastMembers());
        }else if(giftToastRequest.giftToastType() == GiftToastType.MINE){
            giftToastOwnerResponses = saveMineGiftToast(giftToast.getId(), memberId);
        }

        //TODO
        return GiftToastResponse.from(giftToast,iconRepository.getById(giftToastRequest.iconId()).getIcon_image_url(),giftToastOwnerResponses,groupName);
    }

    @Transactional(readOnly = true)
    @Override
    public GiftToastResponses getGiftToast(final long memberId) {
        //멤버가 가진 giftToast들
        List<GiftToast> giftToasts = giftToastRepository.getGiftToastByMemberId(memberId);
        List<GiftToastResponse> giftToastResponses = new ArrayList<>();

        giftToasts.forEach(
                giftToast -> {
                    String groupName = groupRepository.findById(giftToast.getGroupId()).orElseGet(null).getName();
                    String iconImageUrl = iconRepository.getById(giftToast.getIconId()).getIcon_image_url();

                    List<GiftToastOwnerResponse> giftToastOwnerResponse = new ArrayList<>();
                    List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findByGiftToastId(giftToast.getId());

                    giftToastOwners.forEach(
                            giftToastOwner -> {
                                giftToastOwnerResponse.add(GiftToastOwnerResponse.from(
                                        memberRepository.getById(giftToastOwner.getMemberId())));
                            }
                    );

                    giftToastResponses.add(GiftToastResponse.from(giftToast,iconImageUrl, giftToastOwnerResponse,groupName));
                }
        );

        return new GiftToastResponses(giftToastResponses);
    }

    @Transactional
    @Override
    public GiftToastIncompleteResponses getGiftToastIncomplete(final long memberId) {
        List<GiftToast> giftToasts = giftToastRepository.getGiftToastByMemberId(memberId);
        List<GiftToastIncompleteResponse> giftToastIncompleteResponses = new ArrayList<>();

        giftToasts.forEach(
                giftToast -> {
                    List<ToastPiece> toastPiecesByGiftToast = toastPieceRepository.findAllByGiftToastId(giftToast.getId());

                    if(!toastPiecesByGiftToast.stream().filter(toastPiece -> toastPiece.getMemberId()==memberId).findFirst().isPresent()){

                        List<GiftToastIncompleteMember> giftToastMembers = new ArrayList<>();
                        List<GiftToastOwnerResponse>  giftToastOwnerResponses = giftToastOwnerRepository.findAllGiftToastMemberByGiftToastId(giftToast.getId());
                        giftToastOwnerResponses.forEach(
                                giftToastOwner -> {
                                    GiftToastIncompleteMember giftToastIncompleteMember;
                                    if(toastPiecesByGiftToast.stream().filter(toastPiece -> toastPiece.getMemberId() == giftToastOwner.memberId()).findFirst().isPresent()){
                                        giftToastIncompleteMember = GiftToastIncompleteMember.builder()
                                                .memberId(giftToastOwner.memberId())
                                                .nickname(giftToastOwner.nickname())
                                                .complete(true).build();
                                    }else{
                                        giftToastIncompleteMember = GiftToastIncompleteMember.builder()
                                                .memberId(giftToastOwner.memberId())
                                                .nickname(giftToastOwner.nickname())
                                                .complete(false).build();
                                    }
                                    giftToastMembers.add(giftToastIncompleteMember);
                                }
                        );

                        String iconImageUrl = iconRepository.getById(giftToast.getIconId()).getIcon_image_url();
                        giftToastIncompleteResponses.add(GiftToastIncompleteResponse.from(giftToast, iconImageUrl, giftToastMembers));
                    }

                }
        );




        return new GiftToastIncompleteResponses(giftToastIncompleteResponses);
    }

    @Transactional
    @Override
    public void deleteGiftToast(final long memberId,final long giftToastId) {
        List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findByGiftToastId(giftToastId);
        GiftToastOwner myGiftToast = giftToastOwners.stream().filter(giftToastOwner -> giftToastOwner.getMemberId() == memberId).findFirst().get();
        myGiftToast.updateIsVisible(false);
        if(giftToastOwners.stream().filter(giftToastOwner -> giftToastOwner.getIsVisible()==true).count() == 0){
            //giftToast
            giftToastOwners.forEach(
                    giftToastOwner -> giftToastOwnerRepository.delete(giftToastOwner)
            );

            List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToastId);
            toastPieces.forEach(toastPiece -> toastPieceService.deleteToastPiece(toastPiece));

            //giftToast
            giftToastRepository.deleteById(giftToastId);
        }
    }



    private List<GiftToastOwnerResponse> saveGroupGiftToast(final long giftToastId, final GiftToastRequest giftToastRequest){

        List<TeamMember> groupMembers = memberGroupRepository.findAllByMemberGroupId(giftToastRequest.groupId());

        if(groupMembers.isEmpty()){
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }

        List<GiftToastOwnerResponse> giftToastOwnerResponses = new ArrayList<>();

        groupMembers.forEach(
                memberGroup -> {
                    if(giftToastRequest.giftToastMembers().contains(memberGroup.getMemberId())){

                        GiftToastOwner giftToastOwner = giftToastOwnerRepository.save(
                                GiftToastOwner
                                        .builder()
                                        .memberId(memberGroup.getMemberId())
                                        .giftToastId(giftToastId)
                                        .isVisible(true)
                                        .build());

                        giftToastOwnerResponses.add(
                                GiftToastOwnerResponse.from(memberRepository.getById(giftToastOwner.getMemberId())));

                    }else{
                        throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
                    }
                }
        );

        return giftToastOwnerResponses;
    }

    private List<GiftToastOwnerResponse> saveFriendGiftToast(final long giftToastId, final List<Long> giftToastMembers){

        List<GiftToastOwnerResponse> giftToastOwnerResponses = new ArrayList<>();

        giftToastMembers.forEach( giftToastMemberId -> {

            GiftToastOwner giftToastOwner = giftToastOwnerRepository.save(
                    GiftToastOwner
                            .builder()
                            .memberId(giftToastMemberId)
                            .giftToastId(giftToastId)
                            .isVisible(true)
                            .build());

            giftToastOwnerResponses.add(
                    GiftToastOwnerResponse.from(memberRepository.getById(giftToastOwner.getMemberId())));

        });
        return giftToastOwnerResponses;
    }

    private List<GiftToastOwnerResponse> saveMineGiftToast(final long giftToastId, final long memberId){

        GiftToastOwner giftToastOwner = GiftToastOwner
                .builder()
                .memberId(memberId)
                .giftToastId(giftToastId)
                .isVisible(true)
                .build();

        giftToastOwnerRepository.save(giftToastOwner);

        return List.of(GiftToastOwnerResponse.from(memberRepository.getById(memberId)));
    }

}
