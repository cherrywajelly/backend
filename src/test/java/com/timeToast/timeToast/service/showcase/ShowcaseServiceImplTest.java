package com.timeToast.timeToast.service.showcase;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.showcase.Showcase;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseEditResponse;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseEditResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowcaseServiceImplTest {

    @Mock
    private ShowcaseRepository showcaseRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private IconRepository iconRepository;

    @InjectMocks
    private ShowcaseServiceImpl showcaseService;

    private Showcase showcaseSetup(){
        return Showcase.builder()
                .eventToastId(1L)
                .memberId(1L)
                .build();
    }

    private EventToast eventToastSetup(){
        return EventToast.builder()
                .title("title")
                .iconId(1L)
                .memberId(1L)
                .openedDate(LocalDate.now())
                .build();
    }

    private Icon iconSetup(){
        return Icon.builder()
                .iconGroupId(1L)
                .iconImageUrl("iconImageUrl")
                .build();
    }

    private List<Showcase> showcasesSetup(){
        List<Showcase> showcases = new ArrayList<>();

        for(long i = 1; i < 3; i++){
            Showcase showcase = Showcase.builder()
                    .eventToastId(i)
                    .memberId(1L)
                    .build();
            ReflectionTestUtils.setField(showcase, "id", i);
            showcases.add(showcase);
        }
        return showcases;
    }

    private List<EventToast> eventToastsSetup(){
        List<EventToast> eventToasts = new ArrayList<>();

        for(long i = 1; i < 4; i++){
            EventToast eventToast = EventToast.builder()
                    .title("title"+i)
                    .iconId(1L)
                    .memberId(1L)
                    .openedDate(LocalDate.now())
                    .build();

            ReflectionTestUtils.setField(eventToast, "id", i);
            eventToasts.add(eventToast);
        }
        return eventToasts;
    }


    @Test
    @DisplayName("첫 showcase 저장")
    public void saveShowcaseFirst(){
        //given
        List<Showcase> showcases = new ArrayList<>();
        when(showcaseRepository.findAllByMemberId(1L)).thenReturn(showcases);

        EventToast eventToast = eventToastSetup();
        ReflectionTestUtils.setField(eventToast, "id", 1L);
        when(eventToastRepository.getById(1L)).thenReturn(eventToast);

        ShowcaseSaveRequest showcaseSaveRequest = new ShowcaseSaveRequest(List.of(1L));

        //when
        ShowcaseSaveResponses showcaseSaveResponses = showcaseService.saveShowcase(1L, showcaseSaveRequest);

        //then
        assertEquals(1, showcaseSaveResponses.showcaseSaveResponses().size());
        assertEquals(eventToast.getTitle(), showcaseSaveResponses.showcaseSaveResponses().get(0));
    }

    @Test
    @DisplayName("이미 존재하는 showcase에 추가 저장")
    public void saveShowcaseSecond(){
        //given
        List<Showcase> showcases = showcasesSetup();
        when(showcaseRepository.findAllByMemberId(1L)).thenReturn(showcases);

        List<EventToast> eventToasts = eventToastsSetup();
        when(eventToastRepository.getById(3L)).thenReturn(eventToasts.get(2));

        ShowcaseSaveRequest showcaseSaveRequest = new ShowcaseSaveRequest(List.of(3L));

        //when
        ShowcaseSaveResponses showcaseSaveResponses = showcaseService.saveShowcase(1L, showcaseSaveRequest);

        //then
        assertEquals(showcaseSaveRequest.showcases().size(), showcaseSaveResponses.showcaseSaveResponses().size());
        assertEquals(eventToasts.get(2).getTitle(), showcaseSaveResponses.showcaseSaveResponses().get(0));
    }

    @Test
    @DisplayName("진열장 가능 수 초과")
    public void saveShowcasesFail(){
        //given
        List<Showcase> showcases = showcasesSetup();
        when(showcaseRepository.findAllByMemberId(1L)).thenReturn(showcases);

        ShowcaseSaveRequest showcaseSaveRequest = new ShowcaseSaveRequest(List.of(5L, 6L));

        //when then
        assertThrows(BadRequestException.class, ()-> showcaseService.saveShowcase(1L, showcaseSaveRequest));

    }

    @Test
    @DisplayName("쇼케이스 편집 리스트 조회")
    public void getShowcaseSaveList(){
        //given
        List<Showcase> showcases = showcasesSetup();
        when(showcaseRepository.findAllByMemberId(1L)).thenReturn(showcases);

        List<EventToast> eventToasts = eventToastsSetup();
        when(eventToastRepository.findAllByMemberId(1L)).thenReturn(eventToasts);

        Icon icon = iconSetup();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        //when

        ShowcaseEditResponses showcaseEditResponses = showcaseService.getShowcaseSaveList(1L);

        //then
        assertEquals(eventToasts.size(), showcaseEditResponses.showcaseEditResponses().size());
        assertEquals(showcases.size(),showcaseEditResponses.showcaseEditResponses().stream().filter(ShowcaseEditResponse::isShowcase).count());


    }

    @Test
    @DisplayName("등록된 진열장 토스트 조회")
    public void getShowcase(){
        //given
        List<Showcase> showcases = showcasesSetup();
        when(showcaseRepository.findAllByMemberId(1L)).thenReturn(showcases);

        List<EventToast> eventToasts = eventToastsSetup();
        when(eventToastRepository.getById(1L)).thenReturn(eventToasts.get(0));
        when(eventToastRepository.getById(2L)).thenReturn(eventToasts.get(1));


        Icon icon = iconSetup();
        ReflectionTestUtils.setField(icon, "id", 1L);
        when(iconRepository.getById(anyLong())).thenReturn(icon);

        //when
        ShowcaseResponses showcaseResponses = showcaseService.getShowcase(1L);
        //then
        assertEquals(showcases.size(), showcaseResponses.showcaseResponses().size());
    }

    @Test
    @DisplayName("진열장 토스트 삭제")
    public void deleteShowcase(){
        //given
        Showcase showcase = showcaseSetup();
        ReflectionTestUtils.setField(showcase, "id", 1L);
        when(showcaseRepository.findByShowcaseId(1L)).thenReturn(Optional.of(showcase));

        //when
        Response response = showcaseService.deleteShowcase(1L, 1L);

        //then
        assertEquals(StatusCode.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DisplayName("진열장 토스트 삭제 :진열장 조회 실패")
    public void deleteShowcaseFail(){
        //given when then
        assertThrows(NotFoundException.class, ()-> showcaseService.deleteShowcase(1L, 1L));

    }

    @Test
    @DisplayName("진열장 토스트 삭제 : 멤버 아이디 불일치")
    public void deleteShowcaseFailByMemberId(){
        //given
        Showcase showcase = showcaseSetup();
        ReflectionTestUtils.setField(showcase, "id", 1L);
        when(showcaseRepository.findByShowcaseId(1L)).thenReturn(Optional.of(showcase));

        //when then
        assertThrows(BadRequestException.class, ()-> showcaseService.deleteShowcase(2L, 1L));


    }

}