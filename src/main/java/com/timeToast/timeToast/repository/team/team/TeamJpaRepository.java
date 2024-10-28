package com.timeToast.timeToast.repository.team.team;

import com.timeToast.timeToast.domain.team.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Team getTeamById(final long teamId);
}
