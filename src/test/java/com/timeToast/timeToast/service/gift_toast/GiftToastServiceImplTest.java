package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceDetailResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.util.DDayCount;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.team.team.TeamRepository;
import com.timeToast.timeToast.repository.team.team_member.TeamMemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.toast_piece.ToastPieceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftToastServiceImplTest {

    @Mock
    GiftToastRepository giftToastRepository;

    @Mock
    GiftToastOwnerRepository giftToastOwnerRepository;

    @Mock
    ToastPieceService toastPieceService;

    @Mock
    ToastPieceRepository toastPieceRepository;

    @Mock
    ToastPieceImageRepository toastPieceImageRepository;

    @Mock
    TeamRepository teamRepository;

    @Mock
    TeamMemberRepository teamMemberRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    IconRepository iconRepository;

    @Mock
    FcmService fcmService;

    @InjectMocks
    GiftToastServiceImpl giftToastService;

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

    private Icon giftToastIconSetUp(){
        return Icon.builder()
                .iconGroupId(1L)
                .iconImageUrl("imageUrl")
                .build();
    }

    private TeamMember teamMemberSetUp(){
        return TeamMember.builder()
                .teamId(1L)
                .memberId(1L)
                .build();
    }

    private List<TeamMember> teamMembersSetUp(){
        List<TeamMember> teamMembers = new ArrayList<>();

        for (long i = 1; i < 5; i++) {
            teamMembers.add(TeamMember.builder().teamId(1L).memberId(i).build());
        }

        return teamMembers;
    }

    private GiftToast giftToastSetUp(){
        return GiftToast.builder()
                .iconId(1L)
                .teamId(1L)
                .memorizedDate(LocalDate.now())
                .openedDate(LocalDate.now())
                .isOpened(true)
                .title("title")
                .giftToastType(GiftToastType.MINE)
                .build();
    }

    private List<GiftToast> giftToastsSetUp(){
        List<GiftToast> giftToasts = new ArrayList<>();

        for (long i = 1; i < 5; i++) {
            GiftToast giftToast = GiftToast.builder()
                    .iconId(1L)
                    .memorizedDate(LocalDate.now())
                    .openedDate(LocalDate.now())
                    .isOpened(true)
                    .title("title")
                    .giftToastType(GiftToastType.MINE)
                    .build();
            ReflectionTestUtils.setField(giftToast, "id", i);
            giftToasts.add(giftToast);
        }

        for (long i = 5; i < 10; i++) {
            GiftToast giftToast = GiftToast.builder()
                    .iconId(1L)
                    .memorizedDate(LocalDate.now())
                    .openedDate(LocalDate.now())
                    .isOpened(false)
                    .title("title")
                    .giftToastType(GiftToastType.MINE)
                    .build();
            ReflectionTestUtils.setField(giftToast, "id", i);
            giftToasts.add(giftToast);
        }

        return giftToasts;
    }

    private List<GiftToast> giftToastsIncompleteSetUp(){
        List<GiftToast> giftToasts = new ArrayList<>();

        for (long i = 1; i < 10; i++) {
            GiftToast giftToast = GiftToast.builder()
                    .iconId(1L)
                    .memorizedDate(LocalDate.now())
                    .openedDate(LocalDate.now())
                    .isOpened(false)
                    .title("title")
                    .giftToastType(GiftToastType.MINE)
                    .build();
            ReflectionTestUtils.setField(giftToast, "id", i);
            giftToasts.add(giftToast);
        }

        return giftToasts;
    }

    private ToastPiece toastPieceSetUp(final long giftToastId){
        return ToastPiece.builder()
                .giftToastId(giftToastId)
                .memberId(1L)
                .iconId(1L)
                .contentsUrl("contentsUrl")
                .title("title")
                .build();
    }

    private ToastPieceResponse toastPieceResponseSetUp(){
        return ToastPieceResponse.builder()
                .memberId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .profileUrl("profileUrl")
                .toastPieceImages(List.of())
                .toastPieceId(1L)
                .nickname("nickname")
                .contentsUrl("contentsUrl")
                .createdAt(LocalDate.now())
                .build();
    }

    private GiftToastGroupRequest giftToastGroupSuccessSetUp(){
        return new GiftToastGroupRequest(1L, 1L, LocalDate.now(), LocalDate.now(), "title");
    }

    private GiftToastGroupRequest giftToastGroupFailSetUp(){
        return new GiftToastGroupRequest(1L, 1L, LocalDate.now(), LocalDate.now().minusDays(2), "title");
    }

    private GiftToastFriendRequest giftToastFriendSuccessSetUp(){
        return new GiftToastFriendRequest(1L, 1L, LocalDate.now(), LocalDate.now(), "title");
    }

    private GiftToastFriendRequest giftToastFriendFailSetUp(){
        return new GiftToastFriendRequest(1L, 1L, LocalDate.now(), LocalDate.now().minusDays(2), "title");
    }

    private GiftToastMineRequest giftToastMineSuccessSetUp(){
        return new GiftToastMineRequest(1L, LocalDate.now(), LocalDate.now(), "title");
    }

    private GiftToastMineRequest giftToastMineFailSetUp(){
        return new GiftToastMineRequest(1L, LocalDate.now(), LocalDate.now().minusDays(2), "title");
    }

    private GiftToastOwner giftToastOwnerSetUp(){
        return GiftToastOwner.builder()
                .giftToastId(1L)
                .memberId(1L)
                .build();
    }


    @Test
    @DisplayName("그룹 캡슐 토스트 저장 - 실패: 그룹 캡슐 토스트 그룹 불일치")
    public void saveGiftToastGroupTeamMemberFailTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        TeamMember teamMember = teamMemberSetUp();
        ReflectionTestUtils.setField(teamMember, "id", 1L);
        when(teamMemberRepository.findByMemberIdAndTeamId(anyLong(), anyLong())).thenReturn(Optional.empty());

        GiftToastGroupRequest giftToastGroupRequest = giftToastGroupSuccessSetUp();

        //when then
        assertThrows(BadRequestException.class, ()-> giftToastService.saveGiftToastGroup(1L, giftToastGroupRequest));
    }

    @Test
    @DisplayName("그룹 캡슐 토스트 저장 - 실패: 그룹 캡슐 토스트 open date validation 불일치")
    public void saveGiftToastGroupDateValidationFailTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);


        GiftToastGroupRequest giftToastGroupRequest = giftToastGroupFailSetUp();

        //when then
        assertThrows(BadRequestException.class, ()-> giftToastService.saveGiftToastGroup(1L, giftToastGroupRequest));
    }

    @Test
    @DisplayName("그룹 캡슐 토스트 저장 - 성공")
    public void saveGiftToastGroupTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        TeamMember teamMember = teamMemberSetUp();
        ReflectionTestUtils.setField(teamMember, "id", 1L);
        when(teamMemberRepository.findByMemberIdAndTeamId(anyLong(), anyLong())).thenReturn(Optional.of(teamMember));

        GiftToastGroupRequest giftToastGroupRequest = giftToastGroupSuccessSetUp();

        GiftToast giftToast = GiftToastGroupRequest.to(giftToastGroupRequest);
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        when(giftToastRepository.save(any(GiftToast.class))).thenReturn(giftToast);

        List<TeamMember> teamMembers = teamMembersSetUp();
        when(teamMemberRepository.findAllByTeamId(anyLong())).thenReturn(teamMembers);

        when(giftToastOwnerRepository.save(any(GiftToastOwner.class))).thenReturn(giftToastOwnerSetUp());
        when(fcmService.sendMessageTo(anyLong(), any(FcmPostRequest.class))).thenReturn(null);

        //when
        GiftToastSaveResponse giftToastSaveResponse = giftToastService.saveGiftToastGroup(1L, giftToastGroupRequest);

        //then
        assertEquals(giftToastSaveResponse.giftToastId(), giftToast.getId());
        assertEquals(giftToastSaveResponse.title(), giftToast.getTitle());
        assertEquals(giftToastSaveResponse.giftToastType(), giftToast.getGiftToastType());
        assertEquals(giftToastSaveResponse.memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(giftToastSaveResponse.openedDate(), giftToast.getOpenedDate());
        assertEquals(giftToastSaveResponse.isOpened(), false);

    }

    @Test
    @DisplayName("팔로잉 캡슐 토스트 저장 - 실패: 팔로잉 캡슐 토스트 open date validation 불일치")
    public void saveGiftToastFriendDateValidationFailTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        GiftToastFriendRequest giftToastFriendRequest = giftToastFriendFailSetUp();

        //when then
        assertThrows(BadRequestException.class, ()-> giftToastService.saveGiftToastFriend(1L, giftToastFriendRequest));
    }

    @Test
    @DisplayName("팔로잉 캡슐 토스트 저장 - 성공")
    public void saveGiftToastFriendTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        GiftToastFriendRequest giftToastFriendRequest = giftToastFriendSuccessSetUp();

        GiftToast giftToast = GiftToastFriendRequest.to(giftToastFriendRequest);
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        when(giftToastRepository.save(any(GiftToast.class))).thenReturn(giftToast);

        when(giftToastOwnerRepository.save(any(GiftToastOwner.class))).thenReturn(giftToastOwnerSetUp());
        when(fcmService.sendMessageTo(anyLong(), any(FcmPostRequest.class))).thenReturn(null);

        //when
        GiftToastSaveResponse giftToastSaveResponse = giftToastService.saveGiftToastFriend(1L, giftToastFriendRequest);

        //then
        assertEquals(giftToastSaveResponse.giftToastId(), giftToast.getId());
        assertEquals(giftToastSaveResponse.title(), giftToast.getTitle());
        assertEquals(giftToastSaveResponse.giftToastType(), giftToast.getGiftToastType());
        assertEquals(giftToastSaveResponse.memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(giftToastSaveResponse.openedDate(), giftToast.getOpenedDate());
        assertEquals(giftToastSaveResponse.isOpened(), false);

    }

    @Test
    @DisplayName("나에게 캡슐 토스트 저장 - 실패: 나에게 캡슐 토스트 open date validation 불일치")
    public void saveGiftToastMineDateValidationFailTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        GiftToastMineRequest giftToastMineRequest = giftToastMineFailSetUp();

        //when then
        assertThrows(BadRequestException.class, ()-> giftToastService.saveGiftToastMine(1L, giftToastMineRequest));
    }

    @Test
    @DisplayName("나에게 캡슐 토스트 저장")
    public void saveGiftToastMineTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        GiftToastMineRequest giftToastMineRequest = giftToastMineSuccessSetUp();

        GiftToast giftToast = GiftToastMineRequest.to(giftToastMineRequest);
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        when(giftToastRepository.save(any(GiftToast.class))).thenReturn(giftToast);

        when(giftToastOwnerRepository.save(any(GiftToastOwner.class))).thenReturn(giftToastOwnerSetUp());

        //when
        GiftToastSaveResponse giftToastSaveResponse = giftToastService.saveGiftToastMine(1L, giftToastMineRequest);

        //then
        assertEquals(giftToastSaveResponse.giftToastId(), giftToast.getId());
        assertEquals(giftToastSaveResponse.title(), giftToast.getTitle());
        assertEquals(giftToastSaveResponse.giftToastType(), giftToast.getGiftToastType());
        assertEquals(giftToastSaveResponse.memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(giftToastSaveResponse.openedDate(), giftToast.getOpenedDate());
        assertEquals(giftToastSaveResponse.isOpened(), false);
    }

    @Test
    @DisplayName("캡슐 토스트 상세 정보 조회")
    public void getGiftToastDetailTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        GiftToast giftToast = giftToastSetUp();
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        ReflectionTestUtils.setField(giftToast, "createdAt", LocalDateTime.now());

        when(giftToastRepository.findByGiftToastId(1L)).thenReturn(Optional.of(giftToast));

        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);




        //when
        GiftToastDetailResponse giftToastDetailResponse = giftToastService.getGiftToastDetail(1L, 1L);

        //then
        assertEquals(giftToastDetailResponse.giftToastInfo().giftToastId(), giftToast.getId());
        assertEquals(giftToastDetailResponse.giftToastInfo().title(), giftToast.getTitle());
        assertEquals(giftToastDetailResponse.giftToastInfo().iconImageUrl(), icon.getIconImageUrl());
        assertEquals(giftToastDetailResponse.giftToastInfo().giftToastType(), giftToast.getGiftToastType());
        assertEquals(giftToastDetailResponse.giftToastInfo().profileImageUrl(), member.getMemberProfileUrl());
        assertEquals(giftToastDetailResponse.giftToastInfo().memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(giftToastDetailResponse.giftToastInfo().openedDate(), giftToast.getOpenedDate());
        assertEquals(giftToastDetailResponse.giftToastInfo().createdDate(), giftToast.getCreatedAt().toLocalDate());
        assertEquals(giftToastDetailResponse.giftToastInfo().isOpened(), giftToast.getIsOpened());
        assertEquals(giftToastDetailResponse.dDay(),  DDayCount.count(LocalDate.now(),giftToast.getOpenedDate()));

    }

    @Test
    @DisplayName("캡슐 토스트 info 조회")
    public void getGiftToastInfoTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        GiftToast giftToast = giftToastSetUp();
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        ReflectionTestUtils.setField(giftToast, "createdAt", LocalDateTime.now());

        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        //when
        GiftToastInfo giftToastInfo = giftToastService.getGiftToastInfo(1L, giftToast);

        //then
        assertEquals(giftToastInfo.giftToastId(), giftToast.getId());
        assertEquals(giftToastInfo.title(), giftToast.getTitle());
        assertEquals(giftToastInfo.iconImageUrl(), icon.getIconImageUrl());
        assertEquals(giftToastInfo.giftToastType(), giftToast.getGiftToastType());
        assertEquals(giftToastInfo.profileImageUrl(), member.getMemberProfileUrl());
        assertEquals(giftToastInfo.memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(giftToastInfo.openedDate(), giftToast.getOpenedDate());
        assertEquals(giftToastInfo.createdDate(), giftToast.getCreatedAt().toLocalDate());
        assertEquals(giftToastInfo.isOpened(), giftToast.getIsOpened());
    }

    @Test
    @DisplayName("유저 별 캡슐 토스트 조회")
    public void getGiftToastByMemberTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        List<GiftToast> giftToasts = giftToastsSetUp();
        when(giftToastRepository.findAllGiftToastsByMemberId(anyLong())).thenReturn(giftToasts);

        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        //when
        GiftToastResponses giftToastResponses = giftToastService.getGiftToastByMember(1L);

        //then
        verify(giftToastRepository,times(1)).findAllGiftToastsByMemberId(1L);
        verify(memberRepository, times(giftToasts.size())).getById(1L);
        assertEquals(giftToastResponses.giftToastResponses().size(), giftToasts.size());
        assertEquals(giftToastResponses.giftToastResponses().get(0).giftToastOwner(), member.getNickname());

    }

    @Test
    @DisplayName("작성해야할 캡슐 토스트 조회")
    public void getGiftToastIncompleteTest(){
        //given
        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        List<GiftToast> giftToasts = giftToastsIncompleteSetUp();
        when(giftToastRepository.findAllGiftToastsByMemberIdAndNotOpen(anyLong())).thenReturn(giftToasts);

        ToastPiece toastPiece1 = toastPieceSetUp(1L);
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 1L)).thenReturn(List.of(toastPiece1));

        ToastPiece toastPiece2 = toastPieceSetUp(2L);
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 2L)).thenReturn(List.of(toastPiece2));

        ToastPiece toastPiece3 = toastPieceSetUp(3L);
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 3L)).thenReturn(List.of(toastPiece3));

        ToastPiece toastPiece4 = toastPieceSetUp(4L);
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 4L)).thenReturn(List.of(toastPiece4));

        ToastPiece toastPiece5 = toastPieceSetUp(5L);
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 5L)).thenReturn(List.of(toastPiece5));

        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 6L)).thenReturn(List.of());
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 7L)).thenReturn(List.of());
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 8L)).thenReturn(List.of());
        when(toastPieceRepository.findAllByMemberIdAndGiftToastId(1L, 9L)).thenReturn(List.of());

        //when
        GiftToastIncompleteResponses giftToastIncompleteResponses = giftToastService.getGiftToastIncomplete(1L);

        //then
        assertEquals(giftToastIncompleteResponses.giftToastResponses().size(), 4);

    }

    @Test
    @DisplayName("캡슐 토스트 별 토스트 조각 조회")
    public void getToastPieceTest(){
        //given
        ToastPiece toastPiece = toastPieceSetUp(1L);
        ReflectionTestUtils.setField(toastPiece, "id", 1L);
        when(toastPieceRepository.findById( 1L)).thenReturn(Optional.of(toastPiece));

        GiftToast giftToast = giftToastSetUp();
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        ReflectionTestUtils.setField(giftToast, "createdAt", LocalDateTime.now());
        when(giftToastRepository.findByGiftToastId(1L)).thenReturn(Optional.of(giftToast));

        Icon icon = giftToastIconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        ToastPieceResponse toastPieceResponse = toastPieceResponseSetUp();
        when(toastPieceService.getToastPieceResponse(toastPiece.getId())).thenReturn(toastPieceResponse);

        //when
        ToastPieceDetailResponse toastPieceDetailResponse = giftToastService.getToastPiece(1L, 1L);

        //then
        assertEquals(toastPieceDetailResponse.giftToastInfo().giftToastId(), giftToast.getId());
        assertEquals(toastPieceDetailResponse.giftToastInfo().title(), giftToast.getTitle());
        assertEquals(toastPieceDetailResponse.giftToastInfo().iconImageUrl(), icon.getIconImageUrl());
        assertEquals(toastPieceDetailResponse.giftToastInfo().giftToastOwner(), member.getNickname());
        assertEquals(toastPieceDetailResponse.giftToastInfo().giftToastType(), giftToast.getGiftToastType());
        assertEquals(toastPieceDetailResponse.giftToastInfo().profileImageUrl(), member.getMemberProfileUrl());
        assertEquals(toastPieceDetailResponse.giftToastInfo().memorizedDate(), giftToast.getMemorizedDate());
        assertEquals(toastPieceDetailResponse.giftToastInfo().openedDate(), giftToast.getOpenedDate());
        assertEquals(toastPieceDetailResponse.giftToastInfo().createdDate(), LocalDateTime.now().toLocalDate());
        assertEquals(toastPieceDetailResponse.giftToastInfo().isOpened(), giftToast.getIsOpened());


    }


}
