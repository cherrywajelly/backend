package com.timeToast.timeToast.service.search;

import com.timeToast.timeToast.dto.search.SearchRequest;
import com.timeToast.timeToast.dto.search.response.SearchResponse;
import com.timeToast.timeToast.dto.search.response.SearchResponses;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;

    public SearchServiceImpl(final MemberRepository memberRepository, final IconRepository iconRepository) {
        this.memberRepository = memberRepository;
        this.iconRepository = iconRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public SearchResponses searchNickname(final SearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.page(), searchRequest.size());

        List<SearchResponse> searchMembers = new ArrayList<>();
        memberRepository.findMemberByNickname(searchRequest.searchKeyword(), pageable).forEach(
                member -> {
                    String profileUrl = member.getMemberProfileUrl();
                    if(profileUrl == null){
                        profileUrl = iconRepository.getDefaultIcon().getIconImageUrl();
                    }
                    searchMembers.add(SearchResponse.from(member, profileUrl));
                }
        );


        return new SearchResponses(searchRequest.page()+1,searchRequest.size(), searchMembers);
    }
}
