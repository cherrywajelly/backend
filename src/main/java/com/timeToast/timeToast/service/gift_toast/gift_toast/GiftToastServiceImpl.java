package com.timeToast.timeToast.service.gift_toast.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.group_member.GroupMember;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastOwnerResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.gift_toast.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.group_member.GroupMemberRepository;
import com.timeToast.timeToast.repository.member_group.MemberGroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_GIFT_TOAST;

@Service
@Slf4j
public class GiftToastServiceImpl implements GiftToastService{

    private final GiftToastRepository giftToastRepository;
    private final GiftToastOwnerRepository giftToastOwnerRepository;
    private final MemberGroupRepository groupRepository;
    private final ToastPieceRepository toastPieceRepository;
    private final GroupMemberRepository memberGroupRepository;
    private final MemberRepository memberRepository;

    public GiftToastServiceImpl(final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                final MemberGroupRepository groupRepository, final ToastPieceRepository toastPieceRepository,
                                final GroupMemberRepository memberGroupRepository, final MemberRepository memberRepository) {
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.groupRepository = groupRepository;
        this.toastPieceRepository = toastPieceRepository;
        this.memberGroupRepository = memberGroupRepository;
        this.memberRepository = memberRepository;
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

        return GiftToastResponse.from(giftToast,giftToastOwnerResponses,groupName);
    }

    @Override
    public GiftToastResponses getGiftToast(final long memberId) {
        List<GiftToast> giftToasts = giftToastRepository.getGiftToastByMemberId(memberId);
        List<GiftToastResponse> giftToastResponses = new ArrayList<>();

        giftToasts.forEach(
                giftToast -> {

                }
        );

        return new GiftToastResponses(giftToastResponses);
    }

    @Override
    public GiftToastResponses getGiftToastIncomplete(final long memberId) {
        return null;
    }

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



            //ToastPieceImage


            //ToastPiece

            //giftToast
        }
    }

    private List<GiftToastOwnerResponse> saveGroupGiftToast(final long giftToastId, final GiftToastRequest giftToastRequest){

        List<GroupMember> groupMembers = memberGroupRepository.findAllByGroupId(giftToastRequest.groupId());

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
                                GiftToastOwnerResponse.from(memberRepository.getById(giftToastOwner.getMemberId()), false));

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
                    GiftToastOwnerResponse.from(memberRepository.getById(giftToastOwner.getMemberId()),false));

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

        return List.of(GiftToastOwnerResponse.from(memberRepository.getById(memberId), false));
    }

}
