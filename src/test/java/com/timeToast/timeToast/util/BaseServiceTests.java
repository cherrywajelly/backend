package com.timeToast.timeToast.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class BaseServiceTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FollowRepository followRepository;

}
