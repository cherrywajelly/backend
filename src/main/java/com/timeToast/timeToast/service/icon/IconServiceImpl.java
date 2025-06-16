package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.*;
import com.timeToast.timeToast.global.constant.StatusCode;

import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.ICON_MEMBER_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;

@Service
@Slf4j
public class IconServiceImpl implements IconService {
    private final IconGroupRepository iconGroupRepository;
    private final IconMemberRepository iconMemberRepository;

    public IconServiceImpl(final IconGroupRepository iconGroupRepository, final IconMemberRepository iconMemberRepository) {
        this.iconGroupRepository = iconGroupRepository;
        this.iconMemberRepository = iconMemberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponses getToastIconGroupsByUser(final long memberId){
        return getIconGroupsByUser(memberId,IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponses getJamIconGroupsByUser(final long memberId) {
        return getIconGroupsByUser(memberId,IconType.JAM);
    }

    private IconGroupDetailResponses getIconGroupsByUser(final long memberId, final IconType iconType){
        List<IconGroupDetail> iconGroupDetailResponses =
                iconGroupRepository.findAllIconGroupSummaryInfoMemberAndIconType(memberId, iconType)
                        .stream().map(
                                iconGroupSummaryInfo -> {
                                    List<IconResponse> iconResponses = iconGroupRepository.getById(iconGroupSummaryInfo.id())
                                            .getIcons().stream().map(IconResponse::from).toList();

                                    return new IconGroupDetail(iconGroupSummaryInfo, true, iconResponses);

                                }
                        ).toList();

        return new IconGroupDetailResponses(iconGroupDetailResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getAllToastsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupInfoResponses getAllJamsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.JAM);
    }

    private IconGroupInfoResponses getAllIconGroups(final long memberId, final IconType iconType){
        List<IconGroupInfoResponse> iconGroupInfoResponses =
                iconGroupRepository.findAllIconGroupSummaryInfoWithNonBuiltinAndRegisteredByIconType(iconType).stream().map(
                iconGroupSummaryInfo -> {
                    boolean isBuy = iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupSummaryInfo.id()).isPresent();
                    return new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy);
                }
        ).collect(Collectors.toList());

        return new IconGroupInfoResponses(iconGroupInfoResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId) {
        IconGroupSummaryInfo iconGroupSummaryInfo =  iconGroupRepository.getIconGroupSummaryInfo(memberId,iconGroupId);
        boolean isBuy = iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId).isPresent();
        List<IconResponse> iconResponses = iconGroupRepository.getById(iconGroupId).getIcons()
                .stream().map(IconResponse::from).toList();
        return new IconGroupDetail(iconGroupSummaryInfo,isBuy,iconResponses);
    }


    @Transactional
    @Override
    public Response deleteIconGroup(final long memberId, final long iconGroupId){
        IconMember iconMember = iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId)
                .orElseThrow(()-> new NotFoundException(ICON_MEMBER_NOT_FOUND.getMessage()));
        iconMemberRepository.deleteById(iconMember.getId());
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}
