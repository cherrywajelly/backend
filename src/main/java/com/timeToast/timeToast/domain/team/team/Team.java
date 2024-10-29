package com.timeToast.timeToast.domain.team.team;

import com.timeToast.timeToast.domain.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    private String teamProfileUrl;

    @Builder
    public Team(final String name, final String teamProfileUrl){
        this.name = name;
        this.teamProfileUrl = teamProfileUrl;
    }

    public void updateTeamProfileUrl(final String teamProfileUrl){
        this.teamProfileUrl = teamProfileUrl;
    }

}
