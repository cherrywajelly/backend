package com.timeToast.timeToast.controller.month_settlement;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementDetailResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.month_settlement.MonthSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/monthSettlements")
@RestController
@RequiredArgsConstructor
public class MonthSettlementController {

    private final MonthSettlementService monthSettlementService;

    @GetMapping("")
    public MonthSettlementResponses getMonthSettlements(@Login LoginMember loginMember) {
        return monthSettlementService.getMonthSettlements(loginMember.id());
    }


    @GetMapping("/{monthSettlementId}")
    public MonthSettlementDetailResponse getMonthSettlementDetail(@Login LoginMember loginMember, @PathVariable("monthSettlementId") long monthSettlementId) {
        return monthSettlementService.getMonthSettlementDetail(loginMember.id(), monthSettlementId);
    }
}
