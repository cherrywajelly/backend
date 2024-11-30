package com.timeToast.timeToast.controller.month_settlement;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.month_settlement.request.MonthSettlementRequest;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.monthSettlement.MonthSettlementService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/monthSettlements")
@RestController
public class MonthSettlementController {

    private final MonthSettlementService monthSettlementService;

    public MonthSettlementController(MonthSettlementService monthSettlementService) {
        this.monthSettlementService = monthSettlementService;
    }

    @GetMapping("")
    public MonthSettlementResponses getMonthSettlementByYearMonth(@RequestBody MonthSettlementRequest monthSettlementRequest){
        return monthSettlementService.getMonthSettlementByYearMonth(monthSettlementRequest);
    }
}
