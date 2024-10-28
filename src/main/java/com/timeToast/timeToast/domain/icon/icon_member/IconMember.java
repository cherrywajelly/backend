package com.timeToast.timeToast.domain.icon.icon_member;

import com.timeToast.timeToast.domain.BaseTime;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.member.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "icon_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IconMember extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icon_member_id")
    private long id;

    private Long memberId;

    private Long iconGroupId;

    @Builder
    public IconMember(final long memberId, final long iconGroupId){
        this.memberId = memberId;
        this.iconGroupId = iconGroupId;
    }
}
