package com.timeToast.timeToast.domain.template;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    private String templateImageUrl;
}
