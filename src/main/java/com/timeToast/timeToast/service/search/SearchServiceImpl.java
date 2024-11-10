package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.dto.search.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponse;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    private final MemberRepository memberRepository;

    public SearchServiceImpl(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public SearchResponses searchNickname(final SearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());
        List<SearchResponse> searchMembers = memberRepository.findMemberByNickname(searchRequest.searchKeyword(), pageable).stream().map(SearchResponse::from).toList();
        return new SearchResponses(searchRequest.page()+1,searchRequest.size(), searchMembers);
    }
}
