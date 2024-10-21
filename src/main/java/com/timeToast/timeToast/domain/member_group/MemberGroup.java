package com.timeToast.timeToast.domain.member_group;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroup extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id")
    private Long id;

    private String name;

    private String groupProfileUrl;

    @Builder
    public MemberGroup(final String name, final String groupProfileUrl){
        this.name = name;
        this.groupProfileUrl = groupProfileUrl;
    }

    public void updateGroupProfileUrl(final String groupProfileUrl){
        this.groupProfileUrl = groupProfileUrl;
    }

}
