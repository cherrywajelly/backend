package com.timeToast.timeToast.controller.settlement;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.SettlementCreatorInfoResponses;
import com.timeToast.timeToast.dto.settlement.response.SettlementDetailResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.settlement.SettlementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2/settlements")
@RestController
public class SettlementCreatorController {
    private final SettlementService settlementService;

    public SettlementCreatorController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @GetMapping("")
    public SettlementCreatorInfoResponses getSettlementByYearMonth(@Login LoginMember loginMember){
        return settlementService.getSettlementByYearMonthByCreator(loginMember.id());
    }


    @GetMapping("/detail")
    public SettlementDetailResponse getSettlement(@Login LoginMember loginMember,@RequestBody SettlementRequest settlementRequest ){
        return settlementService.getAllSettlementByCreator(loginMember.id(),settlementRequest);
    }


}
