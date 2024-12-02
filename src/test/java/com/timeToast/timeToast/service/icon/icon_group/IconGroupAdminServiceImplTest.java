package com.timeToast.timeToast.service.icon.icon_group;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.icon.icon.IconService;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IconGroupAdminServiceImplTest {

    @Mock
    private IconGroupRepository iconGroupRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private IconService iconService;

    @InjectMocks
    private IconGroupAdminServiceImpl iconGroupAdminService;

    private Member member;
    private IconGroup iconGroup;
    private Icon icon;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        long iconGroupId = 1L;

        member = Member.builder().build();
        iconGroup = IconGroup.builder().memberId(memberId).build();
        icon = Icon.builder().iconGroupId(iconGroupId).iconImageUrl("imageUrl").build();
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
//    @DisplayName("아이콘 그룹 생성 - 잘못된 member id 로의 접근")
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
    @DisplayName("아이콘 그룹 조회 성공")
    void getIconGroupForCreator() {
        // Given
        long memberId = 1L;
        long iconGroupId = 1L;
        ReflectionTestUtils.setField(member, "id", memberId);

        when(iconGroupRepository.findAllByMemberId(memberId)).thenReturn(List.of(iconGroup));

        // When
        IconGroupCreatorResponses iconGroupCreatorResponses = iconGroupAdminService.getIconGroupForCreator(memberId);

        // Then
        assertThat(iconGroupCreatorResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 조회 실패 - 아이콘 미조회")
    void getIconGroupForCreatorFail() {
        // Given
        long memberId = 1L;
        long iconId = 0L;
        ReflectionTestUtils.setField(member, "id", memberId);

        when(iconGroupRepository.findAllByMemberId(memberId)).thenReturn(null);

        // When
        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> iconGroupAdminService.getIconGroupForCreator(memberId));

        // Then
        assertThat(nullPointerException).isNotNull();
    }


    @Test
    @DisplayName("아이콘 그룹 상세 조회 성공")
    void getIconGroupDetailForCreator() {
        // Given
        long memberId = 1L;
        long iconGroupId = 1L;

        // When
        IconGroupCreatorResponses iconGroupCreatorResponses = iconGroupAdminService.getIconGroupForCreator(memberId);

        // Then
        assertThat(iconGroupCreatorResponses).isNotNull();
    }

    @Test
    @DisplayName("아이콘 그룹 상세 조회 실패 - 아이콘 그룹 미조회")
    void getIconGroupDetailForCreatorFail() {
        // Given
        long memberId = 1L;
        long iconGroupId = 1L;

        when(iconGroupRepository.getByIdAndMemberId(iconGroupId, memberId)).thenReturn(Optional.empty());

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> iconGroupAdminService.getIconGroupDetailForCreator(memberId, iconGroupId));
        // Then
        assertThat(exception.getMessage()).isEqualTo(INVALID_ICON_GROUP.getMessage());
    }
}
