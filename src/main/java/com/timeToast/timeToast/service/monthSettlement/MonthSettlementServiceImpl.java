package com.timeToast.timeToast.service.monthSettlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import com.timeToast.timeToast.repository.monthSettlement.MonthSettlementRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public class MonthSettlementServiceImpl implements MonthSettlementService {

    private final MonthSettlementRepository monthSettlementRepository;
    private final PaymentRepository paymentRepository;

    public MonthSettlementServiceImpl(final MonthSettlementRepository monthSettlementRepository,final PaymentRepository paymentRepository) {
        this.monthSettlementRepository = monthSettlementRepository;
        this.paymentRepository = paymentRepository;
    }


//    @Scheduled(cron = "0 0 0 1 * *")
//    @Transactional
//    public void updateMonthSettlement() {
//        paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()).forEach(
//                paymentDto -> {
//                    if(paymentDto.itemType().equals(ItemType.ICON){
//
//                        MonthSettlement monthSettlement = monthSettlementRepository.save(
//                                        MonthSettlement.builder()
//                                                .settlement()
//                                                .revenue()
//                                                .yearMonth()
//                                                .settlementState(SettlementState.BEFORE_SETTLEMENT)
//                                                .settlementDate()
//                                                .build()
//
//                                );
//                    }
//
//                }
//        );
//
//    }


}
