package com.timeToast.timeToast.service.monthSettlement;

import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.repository.monthSettlement.MonthSettlementRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MonthSettlementServiceImpl implements MonthSettlementService {

    public MonthSettlementServiceImpl(final MonthSettlementRepository monthSettlementRepository) {
        this.monthSettlementRepository = monthSettlementRepository;
    }

    private final MonthSettlementRepository monthSettlementRepository;

//    @Scheduled(cron = "0 0 0 0 0 *")
//    @Transactional
//    public void updateMonthSettlement() {
//        List<GiftToast> giftToasts = monthSettlementRepository.findAllGiftToastToOpen();
//
//        giftToasts.forEach(
//                giftToast -> {
//                    List<GiftToastOwner> giftToastOwners = giftToastOwnerRepository.findAllByGiftToastId(giftToast.getId());
//                    List<ToastPiece> toastPieces = toastPieceRepository.findAllByGiftToastId(giftToast.getId());
//
//                    boolean isOpen = giftToastOwners.stream()
//                            .allMatch(giftToastOwner ->
//                                    toastPieces.stream().anyMatch(toastPiece -> toastPiece.getMemberId().equals(giftToastOwner.getMemberId()))
//                            );
//
//                    if(isOpen){
//                        giftToast.updateIsOpened(true);
//                        giftToastOwners.forEach(
//                                giftToastOwner -> sendOpenedMessage(giftToast, giftToastOwner.getMemberId())
//
//                        );
//                    }
//
//                }
//        );
//
//        log.info("update gift toast's is open");
//    }
}
