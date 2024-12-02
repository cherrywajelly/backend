package com.timeToast.timeToast.dto.creator.response;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatorDetailResponse(
        String profileUrl,
        String nickname,
        Bank bank,
        String accountNumber

) {
}
