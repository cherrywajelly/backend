package com.timeToast.timeToast.domain.team.team_member;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    private Long teamId;

    private Long memberId;

    @Builder
    public TeamMember(final long teamId, final long memberId){
        this.teamId = teamId;
        this.memberId = memberId;
    }

}
