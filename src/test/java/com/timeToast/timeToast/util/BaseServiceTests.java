package com.timeToast.timeToast.util;

import com.timeToast.timeToast.repository.jpa.follow.FollowRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import com.timeToast.timeToast.repository.jpa.team.team.TeamRepository;
import com.timeToast.timeToast.repository.jpa.team.team_member.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
