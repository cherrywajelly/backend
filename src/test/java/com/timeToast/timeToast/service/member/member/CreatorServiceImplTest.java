package com.timeToast.timeToast.service.member.member;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.creator.response.CreatorInfoResponse;
import com.timeToast.timeToast.dto.creator_account.response.CreatorAccountResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import com.timeToast.timeToast.dto.member.member.request.CreatorRequest;
import com.timeToast.timeToast.dto.member.member.response.CreatorProfileResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.member.member.response.MemberProfileResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatorServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CreatorAccountRepository creatorAccountRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    CreatorServiceImpl creatorService;

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

    private CreatorAccount creatorAccountSetUp(){
        return CreatorAccount.builder()
                .memberId(1L)
                .bank(Bank.IBK)
                .accountNumber("accountNumber")
                .build();
    }

    private List<IconGroup> iconGroupsSetUp(){
        List<IconGroup> iconGroups = new ArrayList<>();

        for(long i=0; i<5; i++){
            IconGroup iconGroup1 = IconGroup.builder()
                    .memberId(1L)
                    .iconType(IconType.TOAST)
                    .iconBuiltin(IconBuiltin.NONBUILTIN)
                    .name("name")
                    .price(100)
                    .iconState(IconState.REGISTERED)
                    .description("description")
                    .build();

            ReflectionTestUtils.setField(iconGroup1, "id", i);
            iconGroups.add(iconGroup1);
        }

        return iconGroups;
    }

    private List<Icon> iconsSetup(){
        List<Icon> icons = new ArrayList<>();

        for(long i=0; i<5; i++){
            Icon icon = Icon.builder()
                    .iconGroupId(1L)
                    .iconImageUrl("iconImageUrl")
                    .build();

            ReflectionTestUtils.setField(icon, "id", i);
            icons.add(icon);
        }

        return icons;
    }

    private List<Payment> paymentsSetup(){
        List<Payment> payments = new ArrayList<>();

        for(long i=0; i<5; i++){
            Payment payment = Payment.builder()
                    .paymentState(PaymentState.WAITING)
                    .memberId(1L)
                    .amount(100)
                    .itemId(1L)
                    .itemType(ItemType.ICON)
                    .build();

            ReflectionTestUtils.setField(payment, "id", i);
            payments.add(payment);
        }

        return payments;
    }

    private MemberInfoResponse memberProfileResponseSetUp(){
        return new MemberInfoResponse(1L,"nickname","profileUrl","email");
    }



    @Test
    @DisplayName("제작자 프로필 조회")
    public void getCreatorProfile(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        CreatorAccount creatorAccount = creatorAccountSetUp();
        ReflectionTestUtils.setField(creatorAccount, "id", 1L);
        when(creatorAccountRepository.findByMemberId(1L)).thenReturn(Optional.of(creatorAccount));

        List<IconGroup> iconGroups = iconGroupsSetUp();
        when(iconGroupRepository.findAllByMemberId(1L)).thenReturn(iconGroups);

        List<Icon> iconsSetup = iconsSetup();
        when(iconRepository.findAllByIconGroupId(anyLong())).thenReturn(iconsSetup);

        List<Payment> payments = paymentsSetup();
        when(paymentRepository.findAllByItemId(anyLong())).thenReturn(payments);

        //when
        CreatorProfileResponse creatorProfile = creatorService.getCreatorProfile(1L);

        //then
        assertEquals(creatorAccount.getAccountNumber(),creatorProfile.creatorInfoResponse().accountNumber());
        assertEquals(creatorAccount.getBank().value(),creatorProfile.creatorInfoResponse().bank());
        assertEquals(member.getNickname(),creatorProfile.creatorInfoResponse().nickname());
        assertEquals(member.getMemberProfileUrl(),creatorProfile.creatorInfoResponse().profileUrl());
        assertEquals(iconGroups.size(),creatorProfile.iconGroupOrderedResponses().iconGroupOrderedResponses().size());


    }

    @Test
    @DisplayName("제작자 별 아이콘 주문 내역 조회")
    public void getIconOrderedResponse(){
        //given
        List<IconGroup> iconGroups = iconGroupsSetUp();
        when(iconGroupRepository.findAllByMemberId(1L)).thenReturn(iconGroups);

        List<Icon> iconsSetup = iconsSetup();
        when(iconRepository.findAllByIconGroupId(anyLong())).thenReturn(iconsSetup);

        List<Payment> payments = paymentsSetup();
        when(paymentRepository.findAllByItemId(anyLong())).thenReturn(payments);

        //when
        IconGroupOrderedResponses iconGroupOrderedResponses = creatorService.getIconOrderedResponse(1L);

        //then
        assertEquals(iconGroups.size(),iconGroupOrderedResponses.iconGroupOrderedResponses().size());
        verify(iconRepository, times(iconGroups.size())).findAllByIconGroupId(anyLong());
        verify(paymentRepository, times(iconGroups.size())).findAllByItemId(anyLong());

    }


    @Test
    @DisplayName("제작자 정보 조회")
    public void putCreatorInfo(){
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        CreatorAccount creatorAccount = creatorAccountSetUp();
        ReflectionTestUtils.setField(creatorAccount, "id", 1L);
        when(creatorAccountRepository.findByMemberId(1L)).thenReturn(Optional.of(creatorAccount));
        when(creatorAccountRepository.save(any(CreatorAccount.class))).thenReturn(creatorAccount);

        MultipartFile profileImage = mock(MultipartFile.class);
        MemberInfoResponse memberInfoResponse = memberProfileResponseSetUp();
        when(memberService.saveProfileImageByLogin(anyLong(),any(MultipartFile.class) )).thenReturn(memberInfoResponse);

        CreatorRequest creatorRequest = new CreatorRequest("nickname", new CreatorAccountResponse(Bank.KAKAO.value(), "accountNumber"));

        //when
        CreatorInfoResponse creatorInfoResponse = creatorService.putCreatorInfo(1L, profileImage,creatorRequest);

        //then
        assertEquals(member.getNickname(),creatorInfoResponse.nickname());
        assertEquals(creatorAccount.getBank().value(),creatorInfoResponse.bank());
        assertEquals(creatorAccount.getAccountNumber(),creatorInfoResponse.accountNumber());
        assertEquals(memberInfoResponse.profileUrl(),creatorInfoResponse.profileUrl());

    }





}
