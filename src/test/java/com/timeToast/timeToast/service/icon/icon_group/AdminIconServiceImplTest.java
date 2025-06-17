package com.timeToast.timeToast.service.icon.icon_group;

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
import com.timeToast.timeToast.dto.icon.icon.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon.response.CreatorProfileResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupSummaryInfo;
import com.timeToast.timeToast.dto.icon.icon_group.response.admin.*;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.creator.IconGroupOverview;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.service.icon.AdminIconServiceImpl;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
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

    @InjectMocks
    private AdminIconServiceImpl iconGroupAdminService;

    private Member member;

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



    @BeforeEach
    void setUp() {
        long memberId = 1L;

        member = Member.builder().build();
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




    //TODO ...?
//    @Test
//    @DisplayName("아이콘 그룹 상세 조회 성공")
//    void getIconGroupOverview() {
//        // Given
//        long memberId = 1L;
//        long iconGroupId = 1L;
//
//        // When
//        IconGroupOverview iconGroupOverview = iconGroupAdminService.getIconGroupOverview(memberId, iconGroupId);
//
//        // Then
//        assertThat(iconGroupOverview).isNotNull();
//    }

//    @Test
//    @DisplayName("아이콘 그룹 상세 조회 실패 - 아이콘 그룹 미조회")
//    void getIconGroupOverviewFail() {
//        // Given
//        long memberId = 1L;
//        long iconGroupId = 1L;
//
//        when(iconGroupRepository.getByIdAndMemberId(iconGroupId, memberId)).thenReturn(null);
//
//        // When
//        BadRequestException exception = assertThrows(BadRequestException.class, () -> iconGroupAdminService.getIconGroupOverview(memberId, iconGroupId));
//        // Then
//        assertThat(exception.getMessage()).isEqualTo(INVALID_ICON_GROUP.getMessage());
//    }


    @Test
    @DisplayName("아이콘 그룹 상태 저장 :성공")
    void saveIconState() {
        // Given
        IconGroup iconGroup = iconGroupSetUp();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);

        IconGroupStateRequest iconGroupStateRequest = new IconGroupStateRequest(1L, IconState.REGISTERED);

        // When
        IconGroupInfoResponse iconGroupInfoResponse = iconGroupAdminService.saveIconState(iconGroupStateRequest);

        // Then
        assertEquals(iconGroup.getId(), iconGroupInfoResponse.iconGroupId());
        assertEquals(iconGroup.getName(), iconGroupInfoResponse.title());
        assertEquals(iconGroup.getThumbnailImageUrl(), iconGroupInfoResponse.thumbnailUrl());
        assertEquals(iconGroup.getIconType(), iconGroupInfoResponse.iconType());
        assertEquals(iconGroup.getIconState(), iconGroupInfoResponse.iconState());
    }

    @Test
    @DisplayName("icon group overview 조회")
    void getIconGroupOverview() {
        //given
        IconGroup iconGroup = iconGroupSetUp();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(iconGroupRepository.getByIdAndMemberId(iconGroup.getId(), creator.getId())).thenReturn(iconGroup);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        List<Payment> payments = paymentsSetUp();
        when(paymentRepository.findAllByItemId(iconGroup.getId())).thenReturn(payments);

        //when
        IconGroupOverview iconGroupOverview = iconGroupAdminService.getIconGroupOverview(creator.getId(), iconGroup.getId());

        //then
        IconGroupSummaryInfo iconGroupSummaryInfo = IconGroupSummaryInfo.from(iconGroup, creator.getNickname());

        assertEquals(iconGroupSummaryInfo, iconGroupOverview.iconGroupSummaryInfo());

    }

    @Test
    @DisplayName("creator의 icon group overviews 조회")
    void getIconGroupOverviews() {
        //given
        List<IconGroup> iconGroups = iconGroupList();

        Member creator = setUpCreator();
        ReflectionTestUtils.setField(creator, "id", 1L);
        when(iconGroupRepository.findAllByMemberId(creator.getId())).thenReturn(iconGroups);
        when(memberRepository.getById(creator.getId())).thenReturn(creator);

        List<Payment> payments = paymentsSetUp();
        when(paymentRepository.findAllByItemId(anyLong())).thenReturn(payments);

        //when
        CreatorProfileResponse creatorProfileResponse = iconGroupAdminService.getIconGroupOverviews(creator.getId());

        //then
        assertEquals(iconGroups.size(), creatorProfileResponse.iconGroupOverviews().size());

    }

    @Test
    @DisplayName("승인 iconGroup 조회: 성공")
    void getIconGroupForNonApproval() {
        // Given
        List<IconGroup> iconGroups = iconGroupWaitingList();
        when(iconGroupRepository.findAllByIconState(IconState.WAITING)).thenReturn(iconGroups);


        // When
        IconGroupInfoResponses iconGroupInfoResponses = iconGroupAdminService.getIconGroupForNonApproval();

        // Then
        assertEquals(iconGroups.size(), iconGroupInfoResponses.iconGroupNonApprovalResponses().size());
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


        // When
        IconGroupDetailResponse iconGroupDetailResponse = iconGroupAdminService.getIconGroupDetail(1L);

        // Then
        assertEquals(iconGroup.getThumbnailImageUrl(), iconGroupDetailResponse.thumbnailImageUrl());
        assertEquals(iconGroup.getName(), iconGroupDetailResponse.title());
        assertEquals(creator.getNickname(), iconGroupDetailResponse.creatorNickname());
        assertEquals(iconGroup.getPrice(), iconGroupDetailResponse.price());
        assertEquals(iconGroup.getIconState(), iconGroupDetailResponse.iconState());
        assertEquals(iconGroup.getDescription(), iconGroupDetailResponse.description());
        assertEquals(iconGroup.getIcons().size(), iconGroupDetailResponse.icons().size());

    }


    @Test
    @DisplayName("모든 아이콘 그룹 조회: 성공")
    void getAllIconGroups() {
        // Given
        List<IconGroup> iconGroups = iconGroupList();
        when(iconGroupRepository.findAllByIconBuiltin(IconBuiltin.NONBUILTIN)).thenReturn(iconGroups);

        // When
        when(memberRepository.getById(1L)).thenReturn(member);
        IconGroupAdminResponses iconGroupInfoResponses = iconGroupAdminService.getAllIconGroups();

        // Then
        assertEquals(iconGroups.size(), iconGroupInfoResponses.iconGroupAdminResponses().size());

    }


    @Test
    @DisplayName("제작자 별 아이콘 그룹 조회: 성공")
    void getIconGroupsByUserByCreator() {
        // Given
        List<IconGroup> iconGroups = iconGroupList();
        when(iconGroupRepository.findAllByMemberId(anyLong())).thenReturn(iconGroups);

        List<Payment> payments = paymentsSetUp();
        when(paymentRepository.findAllByItemIdAndItemType(anyLong(), any(ItemType.class))).thenReturn(payments);

        List<Icon> icons = iconsSetUp();

        // When
        CreatorIconInfos creatorIconInfos = iconGroupAdminService.getIconGroupsByCreator(1L);

        // Then
        assertEquals(iconGroups.get(0).getName(), creatorIconInfos.creatorIconInfos().get(0).title());
        assertEquals(payments.size()*iconGroups.get(0).getPrice(), creatorIconInfos.creatorIconInfos().get(0).income());
        assertEquals(payments.size(), creatorIconInfos.creatorIconInfos().get(0).salesCount());
        assertEquals(icons.size(), creatorIconInfos.creatorIconInfos().get(0).iconImageUrl().size());


    }

}