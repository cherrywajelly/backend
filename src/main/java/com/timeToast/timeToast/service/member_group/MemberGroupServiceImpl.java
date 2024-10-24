package com.timeToast.timeToast.service.member_group;

import com.timeToast.timeToast.domain.team.team.Team;
import com.timeToast.timeToast.domain.team.team_member.TeamMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.member_group.request.MemberGroupSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponse;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.member_group.group_member.GroupMemberRepository;
import com.timeToast.timeToast.repository.member_group.member_group.MemberGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Service
@Slf4j
public class MemberGroupServiceImpl implements MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    public MemberGroupServiceImpl(final MemberGroupRepository memberGroupRepository, final MemberRepository memberRepository,
                                  final GroupMemberRepository groupMemberRepository) {
        this.memberGroupRepository = memberGroupRepository;
        this.memberRepository = memberRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    @Transactional
    @Override
    public MemberGroupResponse saveGroup(final long memberId, final MemberGroupSaveRequest groupSaveRequest) {

        //save group
        Team memberGroup = Team.builder()
                .name(groupSaveRequest.groupName())
                .build();

        Team saveMemberGroup = memberGroupRepository.save(memberGroup);

        //save member_group
        groupMemberRepository.save(
                TeamMember.builder()
                        .memberGroupId(saveMemberGroup.getId())
                        .memberId(memberId)
                        .build()
        );

        List<Long> groupMembers = groupSaveRequest.groupMembers();

        groupMembers.forEach(
                (groupMemberId) -> {
                    Member findMember = memberRepository.findById(groupMemberId).orElseThrow( () -> new BadRequestException(MEMBER_NOT_FOUND.getMessage()));

                    groupMemberRepository.save(
                            TeamMember.builder()
                                    .memberGroupId(saveMemberGroup.getId())
                                    .memberId(findMember.getId())
                                    .build()
                    );
                }
        );

        return MemberGroupResponse.from(saveMemberGroup);
    }

    @Transactional
    @Override
    public MemberGroupResponse saveGroupImage(final long groupId, final  MultipartFile multipartFile) {

        //s3 로직
        String groupProfileUrl = "";

        Team memberGroup = memberGroupRepository.findById(groupId).orElseThrow(()->
                new NotFoundException(GROUP_NOT_FOUND.getMessage())
        );

        memberGroup.updateTeamProfileUrl(groupProfileUrl);

        return MemberGroupResponse.from(memberGroup);
    }

    @Transactional
    @Override
    public MemberGroupResponses findLoginMemberGroups(final long memberId) {

        List<TeamMember> groupMembers = groupMemberRepository.findAllByMemberId(memberId);
        List<MemberGroupResponse> memberGroupResponses = new ArrayList<>();

        groupMembers.forEach(
                memberGroup -> {
                    Optional<Team> findGroup= memberGroupRepository.findById(memberGroup.getMemberGroupId());
                    if(findGroup.isPresent()){
                        memberGroupResponses.add(
                                MemberGroupResponse.from(findGroup.get())
                        );
                    }

                }
        );

        return new MemberGroupResponses(memberGroupResponses);
    }

    @Transactional
    @Override
    public void deleteMemberGroup(final long memberId, final long memberGroupId) {
        List<TeamMember> groupMembers = groupMemberRepository.findAllByMemberGroupId(memberGroupId);

        groupMembers.stream()
                        .filter((memberGroup -> memberGroup.getMemberId() == memberId))
                        .findFirst().orElseThrow(()-> new NotFoundException(MEMBER_GROUP_NOT_FOUND.getMessage()));

        groupMembers.forEach(
                memberGroup -> groupMemberRepository.delete(memberGroup)
        );

        memberGroupRepository.deleteByMemberGroupId(memberGroupId);

    }

}
