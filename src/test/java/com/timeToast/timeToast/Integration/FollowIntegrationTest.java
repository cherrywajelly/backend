package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class FollowIntegrationTest extends TestContainerSupport {

    @Autowired
    public FollowIntegrationTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private final MemberRepository memberRepository;

    @Test
    @DisplayName("사용자는 팔로우를 하면 팔로잉 계정은 알림을 받을 수 있다.")
    public void tryFollowAndGetNotification(){
        //given
        Member member = Member.builder()
                .email("test@gmail.com")
                .memberRole(MemberRole.USER)
                .memberProfileUrl("https://goo.gl/")
                .build();

        Member saveMember = memberRepository.save(member);

        System.out.println(saveMember.getId());

    }


}

