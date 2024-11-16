package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;

import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_ICON_GROUP;

@Service
@Slf4j
@RequiredArgsConstructor
public class IconGroupServiceImpl implements IconGroupService{
    private final IconGroupRepository iconGroupRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final IconMemberRepository iconMemberRepository;

    @Transactional
    @Override
    public void buyIconGroup(final long memberId, final long iconGroupId){
        Member member = memberRepository.getById(memberId);
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);

        if (member != null && iconGroup != null) {
            // 중복 구매 방지
            if(iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId) == null) {
                iconMemberRepository.save(IconMember.builder()
                        .memberId(memberId)
                        .iconGroupId(iconGroupId)
                        .build());

                log.info("buy new icon group");
            }else {
                throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
            }
        } else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }

    @Transactional
    @Override
    public List<IconGroupResponses> getToastIconGroups(final long memberId){
        return getIconGroups(memberId,IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public List<IconGroupResponses> getJamIconGroups(long memberId) {
        return getIconGroups(memberId,IconType.JAM);
    }

    private List<IconGroupResponses> getIconGroups(final long memberId, final IconType iconType){
        List<IconMember> iconMembers = iconMemberRepository.findByMemberId(memberId);
        List<IconGroupResponses> iconGroupResponses = new ArrayList<>();

        iconMembers.forEach(iconMember -> {
            IconGroup iconGroup = iconGroupRepository.getById(iconMember.getIconGroupId());

            if(iconGroup.getIconType().equals(iconType)){
                List<IconResponse> iconResponses = new ArrayList<>();
                iconRepository.findAllByIconGroupId(iconMember.getIconGroupId())
                        .forEach(icon -> iconResponses.add(new IconResponse(icon.getId(), icon.getIconImageUrl())));

                iconGroupResponses.add(new IconGroupResponses(iconGroup.getId(), iconGroup.getName(), iconResponses));
            }

        });
        return iconGroupResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupMarketResponses getAllToastsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.TOAST);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupMarketResponses getAllJamsIconGroups(final long memberId) {
        return getAllIconGroups(memberId, IconType.JAM);
    }

    private IconGroupMarketResponses getAllIconGroups(final long memberId, final IconType iconType){
        List<IconGroupMarketResponse> iconGroupMarketResponses = new ArrayList<>();
        iconGroupRepository.findAllByIconTypeAndIconBuiltin(iconType, IconBuiltin.NONBUILTIN).forEach(
                iconGroup ->
                    iconGroupMarketResponses.add(
                            IconGroupMarketResponse.builder()
                                    .iconGroupId(iconGroup.getId())
                                    .title(iconGroup.getName())
                                    .thumbnailImageUrl(iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().findFirst().get().getIconImageUrl())
                                    .creatorNickname(memberRepository.getById(iconGroup.getMemberId()).getNickname())
                                    .iconType(iconGroup.getIconType())
                                    .isBuy(iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroup.getId()).isPresent())
                                    .build()
                    ));

        return new IconGroupMarketResponses(iconGroupMarketResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public IconGroupDetailResponse getIconGroupDetail(final long iconGroupId) {
        IconGroup iconGroup = iconGroupRepository.getById(iconGroupId);
        Member creator = memberRepository.getById(iconGroup.getMemberId());
        List<IconResponse> iconResponses = iconRepository.findAllByIconGroupId(iconGroup.getId()).stream().map(IconResponse::from).toList();
        return IconGroupDetailResponse.builder()
                .thumbnailImageUrl(iconResponses.stream().findFirst().get().iconImageUrl())
                .title(iconGroup.getName())
                .creatorNickname(creator.getNickname())
                .price(iconGroup.getPrice())
                .iconResponses(iconResponses)
                .build();
    }

    @Transactional
    @Override
    public void deleteIconGroup(final long memberId, final long iconGroupId){
        IconMember iconMember = iconMemberRepository.getByMemberIdAndIconGroupId(memberId, iconGroupId);
        if (iconMember != null) {
            iconMemberRepository.deleteById(iconMember.getId());
        } else {
            throw new BadRequestException(INVALID_ICON_GROUP.getMessage());
        }
    }
}
