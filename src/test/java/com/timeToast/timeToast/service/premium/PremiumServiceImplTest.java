package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PremiumServiceImplTest {

    @Mock
    private PremiumRepository premiumRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    PremiumServiceImpl premiumService;

    private Member setUpMember() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.USER)
                .build();
    }

    private Premium setUpPremium() {
        return Premium.builder()
                .premiumType(PremiumType.PREMIUM)
                .price(1000)
                .description("test")
                .count(3)
                .build();
    }

    private List<Premium> setUpPremiumList() {
        List<Premium> premiumList = new ArrayList<>();
        Premium premium1 = Premium.builder()
                    .premiumType(PremiumType.BASIC)
                    .price(1000)
                    .description("test")
                    .count(3)
                    .build();
        ReflectionTestUtils.setField(premium1, "id", 1L);

        Premium premium2 = Premium.builder()
                .premiumType(PremiumType.PREMIUM)
                .price(1000)
                .description("test")
                .count(3)
                .build();
        ReflectionTestUtils.setField(premium2, "id", 2L);

        premiumList.add(premium1);
        premiumList.add(premium2);

        return premiumList;
    }

    @Test
    @DisplayName("사용자 프리미엄 저장")
    public void savePremium(){
        //given
        Member member = setUpMember();
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setUpPremium();
        ReflectionTestUtils.setField(premium, "id", 2L);
        when(premiumRepository.getById(2L)).thenReturn(premium);

        //when
        PremiumResponse premiumResponse = premiumService.savePremium(1L, 2L);

        //then
        assertEquals(premium.getId(), premiumResponse.premiumId());
        assertEquals(premium.getPrice(), premiumResponse.price());
        assertEquals(premium.getCount(), premiumResponse.count());
        assertEquals(premium.getDescription(), premiumResponse.description());
        assertEquals(premium.getPremiumType(), premiumResponse.premiumType());
    }

    @Test
    @DisplayName("프리미엄 조회")
    public void getPremium(){
        //given
        List<Premium> premiumList = setUpPremiumList();
        when(premiumRepository.getPremiums()).thenReturn(premiumList);

        //when
        PremiumResponses premiumResponses = premiumService.getPremium();

        // then
        assertEquals(premiumList.size(), premiumResponses.premiumResponses().size());
    }

}