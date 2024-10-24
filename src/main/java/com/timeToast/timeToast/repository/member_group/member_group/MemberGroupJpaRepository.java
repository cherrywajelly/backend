package com.timeToast.timeToast.repository.member_group.member_group;

import com.timeToast.timeToast.domain.team.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberGroupJpaRepository extends JpaRepository<Team, Long> {

    Team getMemberGroupById(final long memberGroupId);
}
