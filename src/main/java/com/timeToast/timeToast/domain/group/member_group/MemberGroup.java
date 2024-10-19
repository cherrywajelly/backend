package com.timeToast.timeToast.domain.group.member_group;

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

    private Long groupId;

    private Long memberId;

    @Builder
    public MemberGroup(final long groupId, final long memberId){
        this.groupId = groupId;
        this.memberId = memberId;
    }

}
