package com.timeToast.timeToast.controller.premium;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.premium.PremiumService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/premiums")
@RestController
public class PremiumController {

    private final PremiumService premiumService;

    public PremiumController(final PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("/{premiumId}")
    public PremiumResponse savePremium(@Login final LoginMember loginMember, @PathVariable final long premiumId){
        return premiumService.savePremium(loginMember.id(), premiumId);
    }
    @GetMapping("")
    public PremiumResponses getPremiums(){
        return premiumService.getPremium();
    }


}
