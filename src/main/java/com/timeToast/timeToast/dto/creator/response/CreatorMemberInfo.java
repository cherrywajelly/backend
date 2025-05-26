package com.timeToast.timeToast.dto.creator.response;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import lombok.Builder;

@Builder
public record CreatorMemberInfo(
        String profileUrl,
        String nickname,
        Bank bank,
        String accountNumber

) {
}
