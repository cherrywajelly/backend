package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.jpa.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon_member.IconMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IconServiceImplTest {


    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconMemberRepository iconMemberRepository;


    @InjectMocks
    private IconServiceImpl iconGroupService;

    private IconGroup setIconGroup() {
        return IconGroup.builder().memberId(1L).build();
    }

    private IconMember setIconMember() {
        return IconMember.builder().build();
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

    @Test
    @DisplayName("유저의 아이콘 그룹 목록 반환 성공: 토스트")
    void getUserIconGroups() {
        //given
        long memberId = 1L;

        //when
        UserIconGroupResponses userIconGroupResponses = iconGroupService.getUserIconGroups(memberId, IconType.TOAST);

        //then
        assertThat(userIconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("유저의 아이콘 그룹 목록 반환 성공: 잼")
    void getJamIconGroupsByUser() {
        long memberId = 1L;

        UserIconGroupResponses userIconGroupResponses = iconGroupService.getUserIconGroups(memberId, IconType.JAM);

        assertThat(userIconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 마켓 목록 반환 성공: 토스트")
    void getMarketIconGroups() {
        long memberId = 1L;

        MarketIconGroupResponses marketIconGroupResponses = iconGroupService.getMarketIconGroups(memberId, IconType.TOAST);

        assertThat(marketIconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 마켓 목록 반환 성공: 잼")
    void getAllJamsIconGroups() {
        long memberId = 1L;

        MarketIconGroupResponses marketIconGroupResponses = iconGroupService.getMarketIconGroups(memberId, IconType.JAM);

        assertThat(marketIconGroupResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 단일 조회 성공")
    void getIconGroupDetailResponse() {
        //given
        IconGroup iconGroup = setIconGroup();
        List<Icon> icons = iconsSetUp();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        ReflectionTestUtils.setField(iconGroup, "icons", icons);
        long memberId = 1L;

        IconGroupInfo iconGroupInfo = IconGroupInfo.from(iconGroup, "creatorNickname");
        when(iconGroupRepository.getIconGroupInfo(memberId,iconGroup.getId())).thenReturn(iconGroupInfo);
        when(iconGroupRepository.getById(iconGroup.getId())).thenReturn(iconGroup);


        //when
        UserIconGroupDetail userIconGroupDetail = iconGroupService.getIconGroupDetail(memberId,iconGroup.getId());

        //then
        assertEquals(iconGroup.getId(), userIconGroupDetail.iconGroupDetail().iconGroupInfo().iconGroupId());
    }

    @Test
    @DisplayName("아이콘 그룹 삭제 성공")
    void deleteIconGroupSuccess() {
        IconGroup iconGroup = setIconGroup();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);

        IconMember iconMember = setIconMember();
        ReflectionTestUtils.setField(iconMember, "id", 1L);

        when(iconMemberRepository.findByMemberIdAndIconGroupId(1L, iconGroup.getId())).thenReturn(Optional.of(iconMember));
        doNothing().when(iconMemberRepository).deleteById(iconMember.getId());

        Response response = iconGroupService.deleteIconGroup(1L, iconGroup.getId());

        verify(iconMemberRepository, times(1)).findByMemberIdAndIconGroupId(1L, iconGroup.getId());
        verify(iconMemberRepository, times(1)).deleteById(iconGroup.getId());
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
