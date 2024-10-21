package com.timeToast.timeToast.domain.group_member;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;

    private Long memberGroupId;

    private Long memberId;

    @Builder
    public GroupMember(final long memberGroupId, final long memberId){
        this.memberGroupId = memberGroupId;
        this.memberId = memberId;
    }

}
