package com.timeToast.timeToast.util;

import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class BaseServiceTests extends TestContainerSupport{

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

}
