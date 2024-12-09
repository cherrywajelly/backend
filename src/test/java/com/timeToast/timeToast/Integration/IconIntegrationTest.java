package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupResponses;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupAdminService;
import com.timeToast.timeToast.service.icon.icon_group.IconGroupService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class IconIntegrationTest extends TestContainerSupport {
    private final IconGroupService iconGroupService;
    private final MemberRepository memberRepository;
    private final IconGroupAdminService iconGroupAdminService;
    private final EventToastService eventToastService;
    private final IconGroupRepository iconGroupRepository;
    private final EventToastRepository eventToastRepository;

    @Autowired
    public IconIntegrationTest(final IconGroupService iconGroupService, final MemberRepository memberRepository,
                               final IconGroupAdminService iconGroupAdminService, final EventToastService eventToastService,
                               final IconGroupRepository iconGroupRepository, final EventToastRepository eventToastRepository) {
        this.iconGroupService = iconGroupService;
        this.memberRepository = memberRepository;
        this.iconGroupAdminService = iconGroupAdminService;
        this.eventToastService = eventToastService;
        this.iconGroupRepository = iconGroupRepository;
        this.eventToastRepository = eventToastRepository;
    }

    @Test
    @DisplayName("사용자는 본인이 소유한 아이콘 중 하나를 선택해 이벤트 토스트를 생성 수 있습니다.")
    public void tryToWriteToastPiece() {
        Member member = memberRepository.getById(1L);

        IconGroupResponses iconGroupResponses = iconGroupService.getToastIconGroups(member.getId());
        assertThat(iconGroupResponses).isNotNull();

        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2025, 1, 1), "title",
                iconGroupResponses.iconGroupResponses().get(0).iconGroupId(), "description");
        ResponseWithId responseWithId = eventToastService.saveEventToast(eventToastPostRequest, 1L);

        EventToast eventToast = eventToastRepository.getById(responseWithId.id());
        assertThat(eventToast.getIconId()).isEqualTo(iconGroupResponses.iconGroupResponses().get(0).iconGroupId());
    }

    @Test
    @DisplayName("사용자는 아이콘 마켓에서 잼 아이콘을 구매해 잼을 바를 수 있습니다.")
    public void tryToBuyJamIcon() {
        Member member1 = memberRepository.getById(1L);
        Member member2 = memberRepository.getById(2L);

        IconGroupPostRequest iconGroupPostRequest = new IconGroupPostRequest("name", 1100, IconType.JAM, IconBuiltin.NONBUILTIN, "description");
        try {
            ClassPathResource imageResource = new ClassPathResource("test_image.jpg");
            MockMultipartFile iconImages = new MockMultipartFile(
                    "iconImages",
                    imageResource.getFilename(),
                    "image/jpeg",
                    imageResource.getInputStream());
            iconGroupAdminService.postIconGroup(iconImages, List.of(iconImages), iconGroupPostRequest, member2.getId());
            IconGroupMarketResponses iconGroupMarketResponses = iconGroupService.getAllJamsIconGroups(member1.getId());

            IconGroupMarketDetailResponse iconGroupMarketDetailResponse = iconGroupService.getIconGroupDetail(member1.getId(), 1L);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
