package com.timeToast.timeToast.repository.team.team;

import com.timeToast.timeToast.domain.team.team.Team;

import java.util.Optional;

public interface TeamRepository {
    Team save(final Team team);
    Team getById(final long teamId);
    Optional<Team> findById(final long teamId);
    void deleteByTeamId(final long teamId);
}
