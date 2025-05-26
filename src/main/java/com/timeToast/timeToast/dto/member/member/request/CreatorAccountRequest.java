package com.timeToast.timeToast.dto.member.member.request;

import com.timeToast.timeToast.domain.enums.member.Bank;
import lombok.Builder;

@Builder
public record CreatorAccountRequest(
        Bank bank,
        String accountNumber
){

}
