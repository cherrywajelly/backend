package com.timeToast.timeToast.domain.template;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    private Long memberId;

    private Long eventToastId;

    @Column(length = 200)
    private String templateText;

    @Builder
    public Template(Long memberId, Long eventToastId, String templateText) {
        this.memberId = memberId;
        this.eventToastId = eventToastId;
        this.templateText = templateText;
    }

    public void updateTemplateText(final String templateText) {
        this.templateText = templateText;
    }
}
