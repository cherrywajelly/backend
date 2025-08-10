package com.timeToast.timeToast.dto.redis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;

import java.time.LocalDate;

public record MemberJoinDto(
        long memberId,
        MemberRole memberRole,
        String nickname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate joinDate
) {
    public static MemberJoinDto from(Member member){
        return new MemberJoinDto(member.getId(), member.getMemberRole(),
                member.getNickname(), LocalDate.from(member.getCreatedAt()));
    }
}
