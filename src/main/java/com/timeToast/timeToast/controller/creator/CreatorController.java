package com.timeToast.timeToast.controller.creator;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.creator.response.CreatorDetailResponse;
import com.timeToast.timeToast.dto.creator.response.CreatorResponses;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.exception.UnauthorizedException;
import com.timeToast.timeToast.service.member.member.MemberService;
import org.springframework.web.bind.annotation.*;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.UNAUTHORIZED_MEMBER;

@RequestMapping("/api/v3/creators")
@RestController
public class CreatorController {

    private final MemberService memberService;

    public CreatorController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public CreatorResponses getCreators(@Login LoginMember loginMember) {
        if(!loginMember.role().equals(MemberRole.MANAGER)){
            throw new UnauthorizedException(UNAUTHORIZED_MEMBER.getMessage());
        }
        return memberService.getCreators();
    }

    @GetMapping("/{creatorId}")
    public CreatorDetailResponse getCreatorByCreatorId(@Login LoginMember loginMember, @PathVariable long creatorId) {
        if(!loginMember.role().equals(MemberRole.MANAGER)){
            throw new UnauthorizedException(UNAUTHORIZED_MEMBER.getMessage());
        }
        return memberService.getCreatorByCreatorId(creatorId);
    }

//    @GetMapping("/monthSettlement")
//    public void getCreatorMonthSettlement(@Login LoginMember loginMember, @RequestParam("year") int year, @RequestParam("month") int month) {
//        if(!loginMember.role().equals(MemberRole.MANAGER)){
//            throw new UnauthorizedException(UNAUTHORIZED_MEMBER.getMessage());
//        }
//        return memberService.getCreatorMonthSettlement(year, month);
//    }
//
//    @GetMapping("/{creatorId}/monthSettlement")
//    public void getCreatorMonthSettlementByCreatorId(@Login LoginMember loginMember, @RequestParam("creatorId") int creatorId,  @RequestParam("year") int year, @RequestParam("month") int month) {
//        if(!loginMember.role().equals(MemberRole.MANAGER)){
//            throw new UnauthorizedException(UNAUTHORIZED_MEMBER.getMessage());
//        }
//        return memberService.getCreatorByCreatorId(creatorId);
//    }

}
