package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IconGroupServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;

    @Mock
    private IconRepository iconRepository;

    @InjectMocks
    private IconGroupServiceImpl iconGroupService;


    private Member member;
    private IconGroup iconGroup;
    private IconMember iconMember;
    private Icon icon;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        long iconGroupId = 1L;
        String imageUrl = "imageUrl";

        member = Member.builder().build();
        iconGroup = IconGroup.builder().memberId(memberId).build();
        iconMember = IconMember.builder().build();
        icon = Icon.builder().iconGroupId(iconGroupId).iconImageUrl(imageUrl).build();
    }


//    @Test
//    @DisplayName("아이콘 그룹 구매 성공")
//    void buyIconGroupSuccess() {
//        long memberId = 1L;
//        long iconGroupId = 1L;
//
//        ReflectionTestUtils.setField(member, "id", memberId);
//        ReflectionTestUtils.setField(iconGroup, "id", iconGroupId);
//
//        when(memberRepository.getById(memberId)).thenReturn(member);
//        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);
//        when(iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId)).thenReturn(null);
//
//        Response response = iconGroupService.buyIconGroup(memberId, iconGroupId);
//
//        verify(iconMemberRepository, times(1)).save(any(IconMember.class));
//        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
//        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
//    }
//
//    @Test
//    @DisplayName("아이콘 그룹 구매 실패 - 이미 구매한 아이콘 존재")
//    void buyIconGroupFailed() {
//        long memberId = 1L;
//        long iconGroupId = 1L;
//
//        ReflectionTestUtils.setField(member, "id", memberId);
//        ReflectionTestUtils.setField(iconGroup, "id", iconGroupId);
//
//        when(memberRepository.getById(memberId)).thenReturn(member);
//        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);
//        when(iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId)).thenReturn(iconMember);
//
//        BadRequestException exception = assertThrows(BadRequestException.class, () -> iconGroupService.buyIconGroup(memberId, iconGroupId));
//
//        assertThat(exception.getMessage()).isEqualTo(INVALID_ICON_GROUP.getMessage());
//        verify(iconMemberRepository, times(1)).getByMemberIdAndIconGroupId(memberId, iconGroupId);
//    }


    @Test
    @DisplayName("토스트 아이콘 그룹 목록 반환 성공")
    void getToastIconGroups() {
        long memberId = 1L;

        IconGroupResponses iconGroupResponses = iconGroupService.getToastIconGroups(memberId);

        assertThat(iconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("잼 아이콘 그룹 목록 반환 성공")
    void getJamIconGroups() {
        long memberId = 1L;

        IconGroupResponses iconGroupResponses = iconGroupService.getJamIconGroups(memberId);

        assertThat(iconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("전체 토스트 아이콘 그룹 목록 반환 성공")
    void getAllToastsIconGroups() {
        long memberId = 1L;

        IconGroupMarketResponses iconGroupMarketResponses = iconGroupService.getAllToastsIconGroups(memberId);

        assertThat(iconGroupMarketResponses).isNotNull();
    }

    @Test
    @DisplayName("전체 잼 아이콘 그룹 목록 반환 성공")
    void getAllJamsIconGroups() {
        long memberId = 1L;

        IconGroupMarketResponses iconGroupMarketResponses = iconGroupService.getAllJamsIconGroups(memberId);

        assertThat(iconGroupMarketResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 마켓 정보 조회 성공")
    void getIconGroupDetailResponse() {
        long iconGroupId = 1L;
        long iconId = 1L;
        IconResponse iconResponse = new IconResponse(iconId, "imageUrl");


        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);
        when(memberRepository.getById(iconGroup.getMemberId())).thenReturn(member);
        when(iconRepository.findAllByIconGroupId(0L)).thenReturn(List.of(icon));

        IconGroupMarketDetailResponse iconGroupDetailResponse = iconGroupService.getIconGroupDetail(iconGroupId);

        assertThat(iconGroupDetailResponse).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 삭제 성공")
    void deleteIconGroupSuccess() {
        long memberId = 1L;
        long iconGroupId = 1L;
        long iconMemberId = 1L;

        ReflectionTestUtils.setField(iconMember, "id", iconMemberId);

        when(iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId)).thenReturn(iconMember);
        doNothing().when(iconMemberRepository).deleteById(iconMember.getId());

        Response response = iconGroupService.deleteIconGroup(memberId, iconGroupId);

        verify(iconMemberRepository, times(1)).getByMemberIdAndIconGroupId(memberId, iconGroupId);
        verify(iconMemberRepository, times(1)).deleteById(iconGroupId);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
    }

    @Test
    @DisplayName("아이콘 그룹 삭제 실패 - 삭제할 아이콘 그룹 조회 실패")
    void deleteIconGroupFail() {
        long memberId = 1L;
        long iconGroupId = 1L;
        long iconMemberId = 1L;

        when(iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId)).thenReturn(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> iconGroupService.deleteIconGroup(memberId, iconGroupId));

        assertThat(exception.getMessage()).isEqualTo(INVALID_ICON_GROUP.getMessage());
        verify(iconMemberRepository, times(1)).getByMemberIdAndIconGroupId(memberId, iconGroupId);
    }
}
