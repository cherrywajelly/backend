package com.timeToast.timeToast.controller.showcase;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.showcase.ShowcaseService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/showcases")
@RestController
public class ShowcaseController {

    private final ShowcaseService showcaseService;

    public ShowcaseController(final ShowcaseService showcaseService) {
        this.showcaseService = showcaseService;
    }

    @PostMapping("")
    public ShowcaseSaveResponse saveShowcase(@Login final LoginMember loginMember, @RequestBody final ShowcaseSaveRequest showcaseSaveRequest){
        return showcaseService.saveShowcase(loginMember.id(), showcaseSaveRequest);
    }

    @GetMapping("")
    public ShowcaseSaveResponses getShowcaseSaveList(@Login final LoginMember loginMember){
        return showcaseService.getShowcaseSaveList(loginMember.id());
    }

    @GetMapping("/members")
    public ShowcaseResponses getShowcaseByLogin(@Login final LoginMember loginMember){
        return showcaseService.getShowcase(loginMember.id());
    }

    @GetMapping("members/{memberId}")
    public ShowcaseResponses getShowcase(@PathVariable final long memberId){
        return showcaseService.getShowcase(memberId);
    }

    @DeleteMapping("/{showcaseId}")
    public void deleteShowcase(@PathVariable final long showcaseId){
        showcaseService.deleteShowcase(showcaseId);
    }

}
