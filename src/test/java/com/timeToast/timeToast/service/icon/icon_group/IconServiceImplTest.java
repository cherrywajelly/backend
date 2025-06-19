package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.IconGroupInfoResponses;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetailResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.icon.IconServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IconServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;

    @Mock
    private IconRepository iconRepository;

    @InjectMocks
    private IconServiceImpl iconGroupService;


    private IconGroup iconGroup;
    private IconMember iconMember;

    @BeforeEach
    void setUp() {
        long memberId = 1L;

        iconGroup = IconGroup.builder().memberId(memberId).build();
        iconMember = IconMember.builder().build();
    }


//    @Test
//    @DisplayName("아이콘 그룹 구매 성공")
//    void buyIconGroupSuccess() {
//        long memberId = 1L;
//        long iconGroupId = 1L;
//
//        ReflectionTestUtils.setField(member, "iconGroupId", memberId);
//        ReflectionTestUtils.setField(iconGroup, "iconGroupId", iconGroupId);
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
//        ReflectionTestUtils.setField(member, "iconGroupId", memberId);
//        ReflectionTestUtils.setField(iconGroup, "iconGroupId", iconGroupId);
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
    void getToastIconGroupsByUser() {
        long memberId = 1L;

        UserIconGroupDetailResponses userIconGroupDetailResponses = iconGroupService.getToastIconGroupsByUser(memberId);

        assertThat(userIconGroupDetailResponses).isNotNull();
    }

    @Test
    @DisplayName("잼 아이콘 그룹 목록 반환 성공")
    void getJamIconGroupsByUser() {
        long memberId = 1L;

        UserIconGroupDetailResponses userIconGroupDetailResponses = iconGroupService.getJamIconGroupsByUser(memberId);

        assertThat(userIconGroupDetailResponses).isNotNull();
    }

    @Test
    @DisplayName("전체 토스트 아이콘 그룹 목록 반환 성공")
    void getAllToastsIconGroups() {
        long memberId = 1L;

        IconGroupInfoResponses iconGroupInfoResponses = iconGroupService.getAllToastsIconGroups(memberId);

        assertThat(iconGroupInfoResponses).isNotNull();
    }

    @Test
    @DisplayName("전체 잼 아이콘 그룹 목록 반환 성공")
    void getAllJamsIconGroups() {
        long memberId = 1L;

        IconGroupInfoResponses iconGroupInfoResponses = iconGroupService.getAllJamsIconGroups(memberId);

        assertThat(iconGroupInfoResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 마켓 정보 조회 성공")
    void getIconGroupDetailResponse() {
        long iconGroupId = 1L;
        long iconId = 1L;
        long memberId = 1L;
        IconResponse iconResponse = new IconResponse(iconId, "imageUrl");

        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);

        UserIconGroupDetail userIconGroupDetail = iconGroupService.getIconGroupDetail(memberId,iconGroupId);

        assertThat(userIconGroupDetail).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 삭제 성공")
    void deleteIconGroupSuccess() {
        long memberId = 1L;
        long iconGroupId = 1L;
        long iconMemberId = 1L;

        ReflectionTestUtils.setField(iconMember, "id", iconMemberId);

        when(iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId)).thenReturn(Optional.of(iconMember));
        doNothing().when(iconMemberRepository).deleteById(iconMember.getId());

        Response response = iconGroupService.deleteIconGroup(memberId, iconGroupId);

        verify(iconMemberRepository, times(1)).findByMemberIdAndIconGroupId(memberId, iconGroupId);
        verify(iconMemberRepository, times(1)).deleteById(iconGroupId);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
    }

    @Test
    @DisplayName("아이콘 그룹 삭제 실패 - 삭제할 아이콘 그룹 조회 실패")
    void deleteIconGroupFail() {
        long memberId = 1L;
        long iconGroupId = 1L;

        assertThrows(NotFoundException.class, () -> iconGroupService.deleteIconGroup(memberId, iconGroupId));
    }
}
