package com.timeToast.timeToast.service.team;

import com.timeToast.timeToast.dto.member_group.request.TeamSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.TeamResponse;
import com.timeToast.timeToast.dto.member_group.response.TeamResponses;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.member.member.MemberService;
import com.timeToast.timeToast.util.BaseServiceTests;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class TeamServiceImplTest extends BaseServiceTests {


    @Mock
    @Autowired
    MemberService memberService;

}
