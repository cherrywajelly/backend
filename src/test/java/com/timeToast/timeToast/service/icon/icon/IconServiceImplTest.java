package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IconServiceImplTest {
    @Mock
    private IconRepository iconRepository;

    @Mock
    private IconGroupRepository iconGroupRepository;

    @InjectMocks
    FileUploadService fileUploadService;

    @InjectMocks
    IconService iconService;

    @Test
    @DisplayName("아이콘 이미지 저장 - 성공")
    void saveToken() {
        // Given
        long iconGroupId = 1L;
        List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class), mock(MultipartFile.class));

        IconGroup iconGroup = IconGroup.builder().build();

        Icon icon1 = new Icon("", iconGroupId);
        Icon icon2 = new Icon("", iconGroupId);

        when(iconGroupRepository.getById(iconGroupId)).thenReturn(iconGroup);
        when(fileUploadService.uploadfile(any(MultipartFile.class), any(String.class))).thenReturn("imageUrl");
        when(iconRepository.save(any(Icon.class))).thenReturn(icon1, icon2);

        // When
        Response response = iconService.postIconSet(files, iconGroupId);

        // Then
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
//
//        if (existMemberToken.isPresent()){
//            existMemberToken.get().updateFcmToken(token);
//            MemberToken savedMemberToken = memberTokenRepository.save(existMemberToken.get());
//
//            // Then
//            assertThat(savedMemberToken.getFcmToken()).isEqualTo(token);
//        }
    }
}
