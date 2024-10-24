package com.timeToast.timeToast.repository.team.team_member;

import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamMemberRepositoryImpl implements TeamMemberRepository {

    private final TeamMemberJpaRepository teamMemberJpaRepository;

    public TeamMemberRepositoryImpl(TeamMemberJpaRepository teamMemberJpaRepository) {
        this.teamMemberJpaRepository = teamMemberJpaRepository;
    }

    @Override
    public TeamMember save(final TeamMember teamMember) {
        return teamMemberJpaRepository.save(teamMember);
    }

    @Override
    public List<TeamMember> findAllByMemberId(final long memberId) {
        return teamMemberJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<TeamMember> findAllByTeamId(final long teamId) {
        return teamMemberJpaRepository.findAllByTeamId(teamId);
    }

    @Override
    public void delete(final TeamMember teamMember) {
        teamMemberJpaRepository.delete(teamMember);
    }
}
