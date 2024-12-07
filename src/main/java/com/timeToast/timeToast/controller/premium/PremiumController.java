package com.timeToast.timeToast.controller.premium;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenues;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.premium.PremiumService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PremiumController {

    private final PremiumService premiumService;

    public PremiumController(final PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("/api/v1/premiums/{premiumId}")
    public PremiumResponse savePremium(@Login final LoginMember loginMember, @PathVariable final long premiumId){
        return premiumService.savePremium(loginMember.id(), premiumId);
    }

    @GetMapping("/api/v1/premiums")
    public PremiumResponses getPremiums(){
        return premiumService.getPremium();
    }

    @GetMapping("/api/v3/premiums/monthly-revenue")
    public PremiumMonthlyRevenues premiumMonthlyRevenue(@RequestParam(value = "year") int year){
        return premiumService.premiumMonthlyRevenue(year);
    }


}
