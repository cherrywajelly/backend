package com.timeToast.timeToast.controller.settlement;

import com.timeToast.timeToast.dto.settlement.request.SettlementDetailRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.SettlementCreatorInfoResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementDetailResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementResponses;
import com.timeToast.timeToast.service.settlement.SettlementService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/settlements")
@RestController
public class SettlementManagerController {

    private final SettlementService settlementService;

    public SettlementManagerController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @PostMapping("")
    public SettlementCreatorInfoResponse approvalSettlement(@RequestBody SettlementDetailRequest settlementDetailRequest){
        return settlementService.approvalSettlement(settlementDetailRequest);
    }

    @GetMapping("")
    public SettlementResponses getMonthSettlement(@RequestBody SettlementRequest settlementRequest){
        return settlementService.getSettlementByYearMonth(settlementRequest);
    }

    @GetMapping("/creators")
    public SettlementDetailResponse getMonthSettlementByYearMonthAndCreator(@RequestBody SettlementDetailRequest settlementDetailRequest){
        return settlementService.getSettlementByYearMonthAndCreator(settlementDetailRequest);
    }
}
