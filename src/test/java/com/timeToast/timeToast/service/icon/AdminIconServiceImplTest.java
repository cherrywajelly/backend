package com.timeToast.timeToast.service.icon;

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
import com.timeToast.timeToast.dto.icon.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminIconServiceImplTest {

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;

    @InjectMocks
    private AdminIconServiceImpl iconGroupAdminService;


    private Member setUpCreator() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.CREATOR)
                .build();
    }

    private IconGroup iconGroupSetUp(){
        return IconGroup.builder()
                .memberId(1L)
                .iconType(IconType.TOAST)
                .iconBuiltin(IconBuiltin.NONBUILTIN)
                .name("name")
                .price(100)
                .iconState(IconState.REGISTERED)
                .description("description")
                .build();
    }

    private List<IconGroup> iconGroupList(){
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
            iconGroup1.addIcons(iconsSetUp());
            iconGroups.add(iconGroup1);
        }

        return iconGroups;
    }

    private List<IconGroup> iconGroupWaitingList(){
        List<IconGroup> iconGroups = new ArrayList<>();

        for(long i=0; i<5; i++){
            IconGroup iconGroup = IconGroup.builder()
                    .memberId(1L)
                    .iconType(IconType.TOAST)
                    .iconBuiltin(IconBuiltin.NONBUILTIN)
                    .name("name")
                    .price(100)
                    .iconState(IconState.WAITING)
                    .description("description")
                    .build();

            ReflectionTestUtils.setField(iconGroup, "id", i);
            iconGroups.add(iconGroup);
        }

        return iconGroups;
    }

    private List<Icon> iconsSetUp(){
        List<Icon> icons = new ArrayList<>();

        for(long i=0; i<5; i++){
            Icon icon = Icon.builder()
                    .iconImageUrl("iconImageUrl")
                    .build();
            ReflectionTestUtils.setField(icon, "id", i);
            icons.add(icon);
        }

        return icons;
    }

    private List<Payment> paymentsSetUp(){
        List<Payment> payments = new ArrayList<>();
        for(long i=0; i<5; i++){
            payments.add(Payment.builder()
                    .paymentState(PaymentState.WAITING)
                    .memberId(1L)
                    .amount(100)
                    .itemId(i)
                    .itemType(ItemType.ICON)
                    .build());
        }
        return payments;
    }



//    @Test
//    @DisplayName("아이콘 그룹 생성 - 성공")
//    void saveIconGroupSuccess() {
//        // Given
//        long memberId = 1L;
//        MockMultipartFile thumbnailIcon = mock(MockMultipartFile.class);
//        ReflectionTestUtils.setField(thumbnailIcon, "originalFilename", "filename");
//        List<MultipartFile> files = List.of(thumbnailIcon);
//        IconGroupPostRequest iconGroupPostRequest = new IconGroupPostRequest("name", 1100, IconType.TOAST, IconBuiltin.BUILTIN, "description");
//
//        when(memberRepository.getById(memberId)).thenReturn(member);
//        when(iconGroupRepository.save(any(IconGroup.class))).thenReturn(iconGroup);
//
//        // When
//        Response response = iconGroupAdminService.postIconGroup(thumbnailIcon, files, iconGroupPostRequest, memberId);
//
//        // Then
//        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
//        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
//    }

//    @Test
//    @DisplayName("아이콘 그룹 생성 - 잘못된 member iconGroupId 로의 접근")
//    void saveIconGroupFail() {
//        // Given
//        long memberId = 1L;
//        IconGroupPostRequest iconGroupPostRequest = new IconGroupPostRequest("name", 1100, IconType.TOAST, IconBuiltin.BUILTIN, "description");
//
//        when(memberRepository.getById(1L)).thenThrow(new BadRequestException("잘못된 아이콘 그룹 형식입니다."));
//
//        // When
//        Throwable throwable = catchThrowable(() -> iconGroupAdminService.postIconGroup(iconGroupPostRequest, memberId));
//
//        // Then
//        assertThat(throwable)
//                .isInstanceOf(BadRequestException.class)
//                .hasMessageContaining(INVALID_ICON_GROUP.getMessage());
//    }

    @Test
    @DisplayName("아이콘 그룹 상태 저장 :성공")
    void saveIconState() {
        // Given
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        IconGroup iconGroup = iconGroupSetUp();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);

        IconGroupStateRequest iconGroupStateRequest = new IconGroupStateRequest(1L, IconState.REGISTERED);

        // When
        IconGroupInfo iconGroupInfo = iconGroupAdminService.saveIconState(iconGroupStateRequest);

        // Then
        assertEquals(iconGroup.getId(), iconGroupInfo.iconGroupId());
        assertEquals(iconGroup.getName(), iconGroupInfo.title());
        assertEquals(iconGroup.getThumbnailImageUrl(), iconGroupInfo.thumbnailImageUrl());
        assertEquals(iconGroup.getIconType(), iconGroupInfo.iconType());
        assertEquals(iconGroup.getIconState(), iconGroupInfo.iconState());
    }



    @Test
    @DisplayName("아이콘 그룹 상세 조회 성공")
    void getCreatorIconGroup() {
        // Given
        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        IconGroup iconGroup = iconGroupSetUp();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);

        when(iconGroupRepository.getByIdAndMemberId(creator.getId(), iconGroup.getId())).thenReturn(iconGroupSetUp());

        List<Payment> payments = paymentsSetUp();
        when(paymentRepository.findAllByItemId(anyLong())).thenReturn(payments);

        // When
        CreatorIconGroup creatorIconGroup = iconGroupAdminService.getCreatorIconGroup(creator.getId(), iconGroup.getId());

        // Then
        assertThat(creatorIconGroup).isNotNull();
    }


    @Test
    @DisplayName("creator의 icon group overviews 조회")
    void getCreatorIconGroups() {
        //given
        List<IconGroup> iconGroups = iconGroupList();

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        when(iconGroupRepository.findAllByMemberId(creator.getId())).thenReturn(iconGroups);

        List<Payment> payments = paymentsSetUp();
        when(paymentRepository.findAllByItemId(anyLong())).thenReturn(payments);

        //when
        CreatorIconGroupResponse creatorIconGroupResponse = iconGroupAdminService.getCreatorIconGroups(creator.getId());

        //then
        assertEquals(iconGroups.size(), creatorIconGroupResponse.creatorIconGroups().size());
    }

    @Test
    @DisplayName("아이콘 그룹 상세 조회: 성공")
    void getIconGroupDetail() {
        // Given
        IconGroup iconGroup = iconGroupSetUp();
        iconGroup.addIcons(iconsSetUp());
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(creator);


        IconGroupInfo iconGroupInfo = IconGroupInfo.from(iconGroup, creator.getNickname());
        // When
        IconGroupDetail iconGroupDetail = iconGroupAdminService.getIconGroupDetail(1L);

        // Then
        assertEquals(iconGroupInfo, iconGroupDetail.iconGroupInfo());
        assertEquals(iconGroup.getIcons().size(), iconGroupDetail.icons().size());

    }

    @Test
    @DisplayName("모든 아이콘 그룹 조회: 성공")
    void getAllIconGroups() {
        // Given
        List<IconGroup> iconGroups = iconGroupList();
        when(iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN)).thenReturn(iconGroups);

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        // When
        IconGroupInfos iconGroupInfos = iconGroupAdminService.getAllIconGroups();

        // Then
        assertEquals(iconGroups.size(), iconGroupInfos.iconGroupInfos().size());
    }

    @Test
    @DisplayName("승인 iconGroup 조회: 성공")
    void getIconGroupForNonApproval() {
        // Given
        List<IconGroup> iconGroups = iconGroupWaitingList();
        when(iconGroupRepository.findAllByIconState(IconState.WAITING)).thenReturn(iconGroups);

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);


        // When
        IconGroupInfos iconGroupInfos = iconGroupAdminService.getIconGroupForNonApproval();

        // Then
        assertEquals(iconGroups.size(), iconGroupInfos.iconGroupInfos().size());
    }



}