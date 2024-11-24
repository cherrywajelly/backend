package com.timeToast.timeToast.service.toast_piece;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.gift_toast.gift_toast_owner.GiftToastOwner;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.toast_piece.toast_piece.ToastPiece;
import com.timeToast.timeToast.domain.toast_piece.toast_piece_image.ToastPieceImage;
import com.timeToast.timeToast.dto.toast_piece.request.ToastPieceRequest;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponse;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceResponses;
import com.timeToast.timeToast.dto.toast_piece.response.ToastPieceSaveResponse;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast_owner.GiftToastOwnerRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece.ToastPieceRepository;
import com.timeToast.timeToast.repository.toast_piece.toast_piece_image.ToastPieceImageRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.BasicImage.BASIC_PROFILE_IMAGE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToastPieceServiceImplTest {

    @Mock
    ToastPieceRepository toastPieceRepository;

    @Mock
    ToastPieceImageRepository toastPieceImageRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    IconRepository iconRepository;

    @Mock
    FileUploadService fileUploadService;

    @Mock
    GiftToastOwnerRepository giftToastOwnerRepository;

    @Mock
    GiftToastRepository giftToastRepository;

    @Mock
    FcmService fcmService;

    @InjectMocks
    ToastPieceServiceImpl toastPieceService;

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

    private ToastPiece toastPieceSetUp(){
        return ToastPiece.builder()
                .giftToastId(1L)
                .memberId(1L)
                .iconId(1L)
                .contentsUrl("contentsUrl")
                .title("title")
                .build();
    }

    private List<ToastPiece> toastPiecesSetUp(){
        List<ToastPiece> toastPieces = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ToastPiece toastPiece = toastPieceSetUp();
            ReflectionTestUtils.setField(toastPiece, "id", 1L);
            toastPieces.add(toastPiece);
        }
        return toastPieces;
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

    private Icon iconSetUp(){
        return Icon.builder()
                .iconGroupId(1L)
                .iconImageUrl("imageUrl")
                .build();
    }

    private GiftToastOwner giftToastOwnerSetUp(){
        return GiftToastOwner.builder()
                .giftToastId(1L)
                .memberId(1L)
                .build();
    }

    private List<ToastPieceImage> toastPieceImagesSetUp(){
        List<ToastPieceImage> toastPieceImages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            toastPieceImages.add(
                    ToastPieceImage.builder()
                            .toastPieceId(1L)
                            .imageUrl("imageUrl").build());
        }

        return toastPieceImages;
    }

    private ToastPieceRequest toastPieceRequestSetUp(){
        return new ToastPieceRequest(1L, 1L, "title");
    }

    @Test
    @DisplayName("토스트 조각 저장 테스트")
    public void saveToastPieceTest() throws IOException {
        //given
        ToastPiece toastPiece = toastPieceSetUp();
        ReflectionTestUtils.setField(toastPiece, "id", 1L);
        when(toastPieceRepository.saveToastPiece(any(ToastPiece.class))).thenReturn(toastPiece);

        GiftToast giftToast = giftToastSetUp();
        ReflectionTestUtils.setField(giftToast, "id", 1L);
        when(giftToastRepository.getById(anyLong())).thenReturn(giftToast);

        GiftToastOwner giftToastOwner = giftToastOwnerSetUp();
        ReflectionTestUtils.setField(giftToastOwner, "id", 1L);
        when(giftToastOwnerRepository.findAllByGiftToastId(anyLong())).thenReturn(List.of(giftToastOwner));


        MockMultipartFile contents = new MockMultipartFile(
                "contents", //name
                "test_image.jpg", //originalFilename
                "JPG",
                new FileInputStream("src/test/resources/test_image.jpg")
        );

        ToastPieceRequest toastPieceRequest = toastPieceRequestSetUp();


        //when
        ToastPieceSaveResponse toastPieceSaveResponse = toastPieceService.saveToastPiece(1L, toastPieceRequest, contents, List.of());

        //then
        assertEquals(toastPiece.getId(),toastPieceSaveResponse.toastPieceId());
        assertEquals(toastPiece.getTitle(), toastPieceSaveResponse.title());
        assertEquals(0,toastPieceSaveResponse.toastPieceImages().size());
        assertEquals(giftToast.getId(),toastPieceSaveResponse.giftToastId());

    }

    @Test
    @DisplayName("캡슐 토스트 별 토스트 조각 조회 테스트")
    public void getToastPiecesByGiftToastIdTest(){
        //given
        ToastPiece toastPiece = toastPieceSetUp();
        ReflectionTestUtils.setField(toastPiece, "id", 1L);
        ReflectionTestUtils.setField(toastPiece, "createdAt", LocalDateTime.now());
        when(toastPieceRepository.findById(1L)).thenReturn(Optional.of(toastPiece));

        Icon icon = iconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        List<ToastPieceImage> toastPieceImages = toastPieceImagesSetUp();
        when(toastPieceImageRepository.findAllByToastPieceId(1L)).thenReturn(toastPieceImages);

        List<ToastPiece> toastPieces = toastPiecesSetUp();
        when(toastPieceRepository.findAllByGiftToastId(1L)).thenReturn(toastPieces);

        //when
        ToastPieceResponses toastPieceResponses = toastPieceService.getToastPiecesByGiftToastId(1L);

        //then
        assertEquals(toastPieces.size(), toastPieceResponses.toastPieceResponses().size());
    }

    @Test
    @DisplayName("토스트 조각 response 조회 테스트 - 실패: 토스트 조각 없음.")
    public void getToastPieceResponseFailTest(){
       //given when then
        assertThrows(NotFoundException.class, ()-> toastPieceService.getToastPieceResponse(1L));
    }

    @Test
    @DisplayName("토스트 조각 response 조회 테스트: 멤버 없음.")
    public void getToastPieceResponseSuccessEmptyMemberTest(){
        //given
        ToastPiece toastPiece = toastPieceSetUp();
        ReflectionTestUtils.setField(toastPiece, "id", 1L);
        ReflectionTestUtils.setField(toastPiece, "createdAt", LocalDateTime.now());
        when(toastPieceRepository.findById(1L)).thenReturn(Optional.of(toastPiece));

        Icon icon = iconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        List<ToastPieceImage> toastPieceImages = toastPieceImagesSetUp();
        when(toastPieceImageRepository.findAllByToastPieceId(1L)).thenReturn(toastPieceImages);

        //when
        ToastPieceResponse toastPieceResponse = toastPieceService.getToastPieceResponse(1L);

        //then
        assertEquals(null, toastPieceResponse.memberId());
        assertEquals(null, toastPieceResponse.nickname());
        assertEquals(BASIC_PROFILE_IMAGE_URL, toastPieceResponse.profileUrl());
        assertEquals(toastPiece.getId(),toastPieceResponse.toastPieceId());
        assertEquals(toastPiece.getTitle(),toastPieceResponse.title());
        assertEquals(toastPiece.getContentsUrl(),toastPieceResponse.contentsUrl());
        assertEquals(toastPiece.getCreatedAt().toLocalDate(),toastPieceResponse.createdAt());
        assertEquals(icon.getIconImageUrl(),toastPieceResponse.iconImageUrl());
        assertEquals(toastPieceImages.size(),toastPieceResponse.toastPieceImages().size());
    }

    @Test
    @DisplayName("토스트 조각 response 조회 테스트: 멤버 있음.")
    public void getToastPieceResponseSuccessMemberTest(){
        ToastPiece toastPiece = toastPieceSetUp();
        ReflectionTestUtils.setField(toastPiece, "id", 1L);
        ReflectionTestUtils.setField(toastPiece, "createdAt", LocalDateTime.now());
        when(toastPieceRepository.findById(1L)).thenReturn(Optional.of(toastPiece));

        Icon icon = iconSetUp();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(1L)).thenReturn(icon);

        Member member = setUpMember();
        ReflectionTestUtils.setField(member, "id", 1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        List<ToastPieceImage> toastPieceImages = toastPieceImagesSetUp();
        when(toastPieceImageRepository.findAllByToastPieceId(1L)).thenReturn(toastPieceImages);

        //when
        ToastPieceResponse toastPieceResponse = toastPieceService.getToastPieceResponse(1L);

        //then
        assertEquals(member.getId(), toastPieceResponse.memberId());
        assertEquals(member.getNickname(), toastPieceResponse.nickname());
        assertEquals(member.getMemberProfileUrl(), toastPieceResponse.profileUrl());
        assertEquals(toastPiece.getId(),toastPieceResponse.toastPieceId());
        assertEquals(toastPiece.getTitle(),toastPieceResponse.title());
        assertEquals(toastPiece.getContentsUrl(),toastPieceResponse.contentsUrl());
        assertEquals(toastPiece.getCreatedAt().toLocalDate(),toastPieceResponse.createdAt());
        assertEquals(icon.getIconImageUrl(),toastPieceResponse.iconImageUrl());
        assertEquals(toastPieceImages.size(),toastPieceResponse.toastPieceImages().size());
    }
}
