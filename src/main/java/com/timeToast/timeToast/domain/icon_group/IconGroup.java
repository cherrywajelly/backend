package com.timeToast.timeToast.domain.icon_group;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "icon_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IconGroup extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icon_group_id")
    private long id;

    private IconType icon_type;

    private String name;

    private long price;

    private IconState state;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    @OneToMany(mappedBy = "iconGroup", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private final Set<Icon> icons = new HashSet<>();
}
