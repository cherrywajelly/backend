package com.timeToast.timeToast.service.gift_toast.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.group.group.Group;
import com.timeToast.timeToast.domain.group.member_group.MemberGroup;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.GiftToastResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.gift_toast.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.group.GroupRepository;
import com.timeToast.timeToast.repository.member_group.MemberGroupRepository;
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
    private final GroupRepository groupRepository;
    private final ToastPieceRepository toastPieceRepository;
    private final MemberGroupRepository memberGroupRepository;

    public GiftToastServiceImpl(final GiftToastRepository giftToastRepository, final GiftToastOwnerRepository giftToastOwnerRepository,
                                final GroupRepository groupRepository, final ToastPieceRepository toastPieceRepository,
                                final MemberGroupRepository memberGroupRepository) {
        this.giftToastRepository = giftToastRepository;
        this.giftToastOwnerRepository = giftToastOwnerRepository;
        this.groupRepository = groupRepository;
        this.toastPieceRepository = toastPieceRepository;
        this.memberGroupRepository = memberGroupRepository;
    }


    @Transactional
    @Override
    public GiftToastResponse saveGiftToast(long memberId, GiftToastRequest giftToastRequest) {

        //TODO 아이콘 확인

        GiftToast giftToast = GiftToastRequest.to(giftToastRequest);

        if(giftToastRequest.giftToastType() == GiftToastType.GROUP){
            Group group = groupRepository.findById(giftToast.getGroupId()).orElseThrow(() -> new BadRequestException(INVALID_GIFT_TOAST.getMessage()));

            List<MemberGroup> memberGroups = memberGroupRepository.findAllByGroupId(group.getId());
            List<Long> giftToastOwnerMembers = new ArrayList<>();

            memberGroups.forEach(
                    memberGroup -> {
                        if(giftToastRequest.giftToastMembers().contains(memberGroup.getMemberId())){

                            GiftToastOwner giftToastOwner = giftToastOwnerRepository.save(
                                        GiftToastOwner
                                                .builder()
                                                .memberId(memberGroup.getMemberId())
                                                .giftToastId(giftToast.getId())
                                                .isVisible(true)
                                                .build());

                                giftToastOwnerMembers.add(giftToastOwner.getMemberId());


                        }else{
                            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
                        }
                    }
            );

            return GiftToastResponse.from(giftToast, giftToastOwnerMembers,group.getName());

        }else if (giftToastRequest.giftToastType() == GiftToastType.FRIEND) {

            if(giftToastRequest.giftToastMembers().size() != 1){
                throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
            }

            List<Long> giftToastOwnerMembers = new ArrayList<>();

            GiftToastOwner owner = giftToastOwnerRepository.save(
                    GiftToastOwner
                        .builder()
                        .memberId(memberId)
                        .giftToastId(giftToast.getId())
                        .isVisible(true)
                        .build());

            giftToastOwnerMembers.add(owner.getMemberId());

            giftToastRequest.giftToastMembers().forEach( giftToastMemberId -> {


                GiftToastOwner giftToastOwner = giftToastOwnerRepository.save(
                        GiftToastOwner
                                .builder()
                            .memberId(giftToastMemberId)
                            .giftToastId(giftToast.getId())
                            .isVisible(true)
                            .build());

                giftToastOwnerMembers.add(giftToastOwner.getMemberId());

            });

            return GiftToastResponse.from(giftToast, giftToastOwnerMembers,null);


        }else if(giftToastRequest.giftToastType() == GiftToastType.MINE){

            GiftToastOwner giftToastOwner = GiftToastOwner
                    .builder()
                    .memberId(memberId)
                    .giftToastId(giftToast.getId())
                    .isVisible(true)
                    .build();

            giftToastOwnerRepository.save(giftToastOwner);

            return GiftToastResponse.from(giftToast, List.of(memberId),null);

        }else{
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
    }
}
