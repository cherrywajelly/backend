package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.search.request.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    SearchServiceImpl searchService;


    private List<Member> setUpMembers(){
        List<Member> members = new ArrayList<>();

        for (long i = 0; i < 10; i++) {
            Member member = Member.builder().nickname("test"+i).memberProfileUrl("testProfileImage").build();
            ReflectionTestUtils.setField(member, "id", i);
            members.add(member);
        }
        return members;
    }

    @Test
    @DisplayName("검색 결과 조회")
    public void searchNicknameTest() {
        //given
        SearchRequest searchRequest = new SearchRequest(0,10, "test");
        List<Member> members = setUpMembers();

        when(memberRepository.findMemberByNickname(any(String.class),any(Pageable.class))).thenReturn(members);

        //when
        SearchResponses searchResponses = searchService.searchNickname(searchRequest);

        //then
        assertEquals(searchRequest.size(),searchResponses.searchResponses().size());
    }



}
