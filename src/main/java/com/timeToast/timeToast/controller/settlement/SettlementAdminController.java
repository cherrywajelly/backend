package com.timeToast.timeToast.controller.settlement;

import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.SettlementApprovalResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementCreatorInfoResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementDetailResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementResponses;
import com.timeToast.timeToast.service.settlement.SettlementService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/settlements")
@RestController
public class SettlementAdminController {

    private final SettlementService settlementService;

    public SettlementAdminController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @PostMapping("/creators/{creatorId}")
    public SettlementApprovalResponse approvalSettlement(@PathVariable long creatorId, @RequestBody SettlementRequest settlementRequest){
        return settlementService.approvalSettlement(creatorId, settlementRequest);
    }

    @GetMapping("")
    public SettlementResponses getMonthSettlement(@RequestParam("year") final int year, @RequestParam("month") final int month){
        return settlementService.getSettlementByYearMonth(year, month);
    }

    @GetMapping("/creators/{creatorId}")
    public SettlementDetailResponse getMonthSettlementByYearMonthAndCreator(@PathVariable long creatorId, @RequestParam("year") final int year, @RequestParam("month") final int month){
        return settlementService.getSettlementByYearMonthAndCreator(creatorId,year, month);
    }
}
