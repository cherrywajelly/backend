package com.timeToast.timeToast.service.month_settlement;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.monthSettlement.MonthSettlement;
import com.timeToast.timeToast.domain.orders.Orders;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOrderedResponses;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementDetailResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.monthSettlement.MonthSettlementRepository;
import com.timeToast.timeToast.repository.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonthSettlementServiceImpl implements MonthSettlementService {

    private final MonthSettlementRepository monthSettlementRepository;
    private final MemberRepository memberRepository;
    private final IconGroupRepository iconGroupRepository;
    private final IconRepository iconRepository;
    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    @Override
    public MonthSettlementResponses getMonthSettlements(final long creatorId) {
        List<MonthSettlement> monthSettlements = monthSettlementRepository.findAllByMemberId(creatorId);
        List<MonthSettlementResponse> monthSettlementResponses = new ArrayList<>();

        monthSettlements.forEach(monthSettlement -> {
            monthSettlementResponses.add(MonthSettlementResponse.from(monthSettlement));
        });
        return new MonthSettlementResponses(monthSettlementResponses);
    }


    @Transactional(readOnly = true)
    @Override
    public MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId) {
        Member member = memberRepository.getById(memberId);
        MonthSettlement monthSettlement = monthSettlementRepository.getById(monthSettlementId);

        List<IconGroupOrderedResponse> iconGroupOrderedResponses = new ArrayList<>();
        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);
        iconGroups.forEach(iconGroup -> {
            List<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId());
            List<String> iconImageUrls = new ArrayList<>();
            icon.forEach(iconImage -> iconImageUrls.add(iconImage.getIconImageUrl()));

            List<Orders> orders = ordersRepository.findAllByIconGroupIdAndCreatedAtMonth(iconGroup.getId(), monthSettlement.getMonth().toString());
            long income = orders.stream()
                    .mapToLong(Orders::getPayment)
                    .sum();

            iconGroupOrderedResponses.add(IconGroupOrderedResponse.of(iconGroup.getName(), iconImageUrls, orders.size(), income));
        });

        long selledIconCount = iconGroupOrderedResponses.stream().mapToLong(IconGroupOrderedResponse::orderCount).sum();
        return MonthSettlementDetailResponse.from(member, monthSettlement.getSettlement(), selledIconCount, new IconGroupOrderedResponses(iconGroupOrderedResponses));
    }
}