package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.response.*;
import com.timeToast.timeToast.global.constant.StatusCode;

import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.jpa.icon.icon_member.IconMemberRepository;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.jpa.icon.icon_group.IconGroupRepository;
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
    public UserIconGroupResponses getUserIconGroups(final long memberId, final IconType iconType){
        List<UserIconGroupDetail> userIconGroupDetailResponses =
                iconGroupRepository.findAllIconGroupInfoByMemberIdAndIconType(memberId, iconType)
                        .stream().map(
                                iconGroupSummaryInfo -> {
                                    List<IconResponse> iconResponses = iconGroupRepository.getById(iconGroupSummaryInfo.iconGroupId())
                                            .getIcons().stream().map(IconResponse::from).toList();
                                    IconGroupDetail iconGroupDetail = new IconGroupDetail(iconGroupSummaryInfo,iconResponses);
                                    return new UserIconGroupDetail(iconGroupDetail, true);

                                }
                        ).toList();

        return new UserIconGroupResponses(userIconGroupDetailResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public MarketIconGroupResponses getMarketIconGroups(final long memberId, final IconType iconType) {
        List<IconGroupInfoResponse> iconGroupInfoResponses =
                iconGroupRepository.findAllIconGroupInfoWithNonBuiltinAndRegisteredByIconType(iconType).stream().map(
                        iconGroupSummaryInfo -> {
                            boolean isBuy = iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupSummaryInfo.iconGroupId()).isPresent();
                            return new IconGroupInfoResponse(iconGroupSummaryInfo, isBuy);
                        }
                ).collect(Collectors.toList());
        return new MarketIconGroupResponses(iconGroupInfoResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public UserIconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId) {
        IconGroupInfo iconGroupInfo =  iconGroupRepository.getIconGroupInfo(memberId,iconGroupId);
        boolean isBuy = iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroupId).isPresent();
        List<IconResponse> iconResponses = iconGroupRepository.getById(iconGroupId).getIcons()
                .stream().map(IconResponse::from).toList();
        IconGroupDetail iconGroupDetail = new IconGroupDetail(iconGroupInfo,iconResponses);
        return new UserIconGroupDetail(iconGroupDetail,isBuy);
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
