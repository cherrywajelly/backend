package com.timeToast.timeToast.domain.template;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TemplateMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_member_id")
    private Long id;

    private Long memberId;

    private Long templateId;

//    private Long eventToastId;
}
