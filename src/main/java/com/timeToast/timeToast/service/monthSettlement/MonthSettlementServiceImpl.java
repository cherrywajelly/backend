package com.timeToast.timeToast.service.monthSettlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import com.timeToast.timeToast.dto.payment.PaymentDto;
import com.timeToast.timeToast.repository.monthSettlement.MonthSettlementRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class MonthSettlementServiceImpl implements MonthSettlementService {

    private final MonthSettlementRepository monthSettlementRepository;
    private final PaymentRepository paymentRepository;

    public MonthSettlementServiceImpl(final MonthSettlementRepository monthSettlementRepository,final PaymentRepository paymentRepository) {
        this.monthSettlementRepository = monthSettlementRepository;
        this.paymentRepository = paymentRepository;
    }


    public void test(){
        System.out.println(paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void updateMonthSettlement() {
        System.out.println(paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()));
//        paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()).stream()
//                .map(Collectors.groupingBy(PaymentDto::memberId)).toList().forEach(
//                paymentDtos -> {
//
//                    monthSettlementRepository.save(
//                            MonthSettlement.builder()
//                                    .memberId(paymentDtos)
//                                    .settlement((long) (paymentDto.salesRevenue()*0.7))
//                                    .revenue(paymentDto.salesRevenue())
//                                    .yearMonth(LocalDate.now())
//                                    .settlementState(SettlementState.BEFORE_SETTLEMENT)
//                                    .settlementDate(null)
//                                    .build());
//
//
//                }
//        );

    }


}
