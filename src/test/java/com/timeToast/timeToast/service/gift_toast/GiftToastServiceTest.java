package com.timeToast.timeToast.service.gift_toast;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastFriendRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastGroupRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastMineRequest;
import com.timeToast.timeToast.dto.gift_toast.request.GiftToastRequest;
import com.timeToast.timeToast.dto.gift_toast.response.*;
import com.timeToast.timeToast.dto.member.member.response.MemberInfoResponse;
import com.timeToast.timeToast.dto.toast_piece.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.GIFT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_GIFT_TOAST;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

public class GiftToastServiceTest implements GiftToastService {

    @Override
    public GiftToastSaveResponse saveGiftToastGroup(long memberId, GiftToastGroupRequest giftToastGroupRequest) {
        if (giftToastGroupRequest.teamId() == 2L) {
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }

        if ((giftToastGroupRequest.openedDate().isBefore(LocalDate.now()))) {
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
        return new GiftToastSaveResponse(1, "title", GiftToastType.GROUP, "description", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastSaveResponse saveGiftToastFriend(long memberId, GiftToastFriendRequest giftToastFriendRequest) {
        if ((giftToastFriendRequest.openedDate().isBefore(LocalDate.now()))) {
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
        return new GiftToastSaveResponse(1, "title", GiftToastType.FRIEND, "description", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastSaveResponse saveGiftToastMine(long memberId, GiftToastMineRequest giftToastMineRequest) {
        if ((giftToastMineRequest.openedDate().isBefore(LocalDate.now()))) {
            throw new BadRequestException(INVALID_GIFT_TOAST.getMessage());
        }
        return new GiftToastSaveResponse(1, "title", GiftToastType.MINE, "description", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1), false);
    }

    @Override
    public GiftToastDetailResponse getGiftToastDetail(long memberId, long giftToastId) {

        if (giftToastId == 2L) {
            throw new NotFoundException(GIFT_TOAST_NOT_FOUND.getMessage());
        }

        List<MemberInfoResponse> memberInfoResponses = new ArrayList<>();
        memberInfoResponses.add(MemberInfoResponse.builder()
                .memberId(1L)
                .email("email")
                .nickname("nickname")
                .profileUrl("profileUrl")
                .build());

        GiftToastTeamMember giftToastTeamMember = new GiftToastTeamMember(3, 1, memberInfoResponses);
        GiftToastInfo giftToastInfo = GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .description("description")
                .isOpened(false)
                .build();

        List<ToastPieceResponse> toastPieceResponses = new ArrayList<>();
        toastPieceResponses.add(
                ToastPieceResponse.builder()
                        .memberId(1L)
                        .toastPieceId(1L)
                        .nickname("nickname")
                        .profileUrl("profileUrl")
                        .iconImageUrl("iconImageUrl")
                        .title("title")
                        .contentsUrl("contentsUrl")
                        .createdAt(LocalDate.now())
                        .toastPieceImages(List.of("images"))
                        .build());

        return new GiftToastDetailResponse(giftToastInfo, giftToastTeamMember, 1L, new ToastPieceResponses(1L, toastPieceResponses));
    }

    @Override
    public GiftToastInfo getGiftToastInfo(final long memberId, final GiftToast giftToast) {
        return GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .description("description")
                .isOpened(false)
                .build();
    }

    @Override
    public GiftToastResponses getGiftToastByMember(long memberId) {
        List<GiftToastResponse> giftToastResponses = new ArrayList<>();
        giftToastResponses.add(
                new GiftToastResponse(1, "title", "iconImageUrl", GiftToastType.GROUP, "owner", false)
        );
        return new GiftToastResponses(giftToastResponses);
    }

    @Override
    public GiftToastIncompleteResponses getGiftToastIncomplete(long memberId) {
        List<GiftToastIncompleteResponse> giftToastResponses = new ArrayList<>();
        giftToastResponses.add(
                new GiftToastIncompleteResponse(1, "title", "iconImageUrl")
        );
        return new GiftToastIncompleteResponses(giftToastResponses);
    }

    @Override
    public ToastPieceDetailResponse getToastPiece(long memberId, long toastPieceId) {
        GiftToastInfo giftToastInfo = GiftToastInfo.builder()
                .giftToastId(1L)
                .title("title")
                .iconImageUrl("iconImageUrl")
                .giftToastType(GiftToastType.GROUP)
                .giftToastOwner("giftToastOwner")
                .profileImageUrl("profileImageUrl")
                .memorizedDate(LocalDate.of(2024, 1, 1))
                .openedDate(LocalDate.of(2024, 1, 1))
                .createdDate(LocalDate.of(2024, 1, 1))
                .description("description")
                .isOpened(false)
                .build();

        ToastPieceResponse toastPieceResponse = ToastPieceResponse.builder()
                .memberId(1L)
                .toastPieceId(1L)
                .nickname("nickname")
                .profileUrl("profileUrl")
                .iconImageUrl("iconImageUrl")
                .title("title")
                .contentsUrl("contentsUrl")
                .createdAt(LocalDate.now())
                .toastPieceImages(List.of("images"))
                .build();

        return new ToastPieceDetailResponse(giftToastInfo, toastPieceResponse);
    }

    @Override
    public Response deleteGiftToast(long memberId, long giftToastId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Override
    public void deleteAllGiftToast(long memberId) {

    }

    @Override
    public GiftToastManagerResponses getGiftToastsForManager() {
        List<GiftToastManagerResponse> giftToastManagerResponses = new ArrayList<>();
        giftToastManagerResponses.add(GiftToastManagerResponse.from(1L, "iconImageUrl", "title", "name"));
        return new GiftToastManagerResponses(giftToastManagerResponses);
    }

    @Override
    public GiftToastInfoManagerResponse getGiftToastInfoForManager(final long giftToastId) {
        List<ToastPieceManagerResponse> toastPieceManagerResponses = new ArrayList<>();
        toastPieceManagerResponses.add(new ToastPieceManagerResponse(1L, "iconImageUrl", "title", LocalDate.of(2024, 1, 1), "nickname"));
        return new GiftToastInfoManagerResponse(1L, "imageUrl", "title", "name", LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1), false, GiftToastType.FRIEND, LocalDate.of(2024, 1, 1), toastPieceManagerResponses);
    }

    @Override
    public GiftToastRequest editGiftToast(final long giftToastId, final GiftToastRequest giftToastRequest) {
        return giftToastRequest;
    }
}