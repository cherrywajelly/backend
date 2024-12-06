package com.timeToast.timeToast.repository.team.team;

import com.timeToast.timeToast.domain.team.team.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamJpaRepository teamJpaRepository;

    public TeamRepositoryImpl(TeamJpaRepository teamJpaRepository) {
        this.teamJpaRepository = teamJpaRepository;
    }

    @Override
    public Team save(final Team team){
        return teamJpaRepository.save(team);
    }

    @Override
    public Team getById(final long teamId) {
        return teamJpaRepository.getTeamById(teamId);
    }

    @Override
    public Optional<Team> findById(final long teamId){
        return teamJpaRepository.findById(teamId);
    }

    @Override
    public void deleteByTeamId(final long teamId) {
        teamJpaRepository.deleteById(teamId);
    }

    @Override
    public List<Team> findAll(){ return teamJpaRepository.findAll(); }

}
