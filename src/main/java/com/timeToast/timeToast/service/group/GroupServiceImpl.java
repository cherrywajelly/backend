package com.timeToast.timeToast.service.group;

import com.timeToast.timeToast.domain.group.Group;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_group.MemberGroup;
import com.timeToast.timeToast.dto.group.request.GroupSaveRequest;
import com.timeToast.timeToast.dto.group.response.GroupResponse;
import com.timeToast.timeToast.dto.group.response.GroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.group.GroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import com.timeToast.timeToast.repository.member_group.MemberGroupRepository;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;

    public GroupServiceImpl(final GroupRepository groupRepository, final MemberRepository memberRepository,
                            final MemberGroupRepository memberGroupRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.memberGroupRepository = memberGroupRepository;
    }

    @Transactional
    @Override
    public GroupResponse saveGroup(final long memberId, final GroupSaveRequest groupSaveRequest) {

        //save group
        Group group = Group.builder()
                .name(groupSaveRequest.groupName())
                .build();

        Group saveGroup = groupRepository.save(group);

        //save member_group
        memberGroupRepository.save(
                MemberGroup.builder()
                        .groupId(saveGroup.getId())
                        .memberId(memberId)
                        .build()
        );

        List<Long> groupMembers = groupSaveRequest.groupMembers();

        groupMembers.forEach(
                (groupMemberId) -> {
                    Member findMember = memberRepository.findById(groupMemberId).orElseThrow( () -> new BadRequestException(MEMBER_NOT_FOUND.getMessage()));

                    memberGroupRepository.save(
                            MemberGroup.builder()
                                    .groupId(saveGroup.getId())
                                    .memberId(findMember.getId())
                                    .build()
                    );
                }
        );

        return GroupResponse.from(saveGroup);
    }

    @Transactional
    @Override
    public GroupResponse saveGroupImage(final long groupId, final  MultipartFile multipartFile) {

        //s3 로직
        String groupProfileUrl = "";

        Group group = groupRepository.findById(groupId).orElseThrow(()->
                new NotFoundException(GROUP_NOT_FOUND.getMessage())
        );

        group.updateGroupProfileUrl(groupProfileUrl);

        return GroupResponse.from(group);
    }

    @Transactional
    @Override
    public GroupResponses findLoginMemberGroups(final long memberId) {

        List<MemberGroup> memberGroups = memberGroupRepository.findAllByMemberId(memberId);
        List<GroupResponse> groupResponses = new ArrayList<>();

        memberGroups.forEach(
                memberGroup -> {
                    Optional<Group> findGroup= groupRepository.findById(memberGroup.getGroupId());
                    if(findGroup.isPresent()){
                        groupResponses.add(
                                GroupResponse.from(findGroup.get())
                        );
                    }

                }
        );

        return new GroupResponses(groupResponses);
    }

    @Transactional
    @Override
    public void deleteGroup(final long memberId, final long groupId) {
        List<MemberGroup> memberGroups = memberGroupRepository.findAllByGroupId(groupId);

        memberGroups.stream()
                        .filter((memberGroup -> memberGroup.getMemberId() == memberId))
                        .findFirst().orElseThrow(()-> new NotFoundException(MEMBER_GROUP_NOT_FOUND.getMessage()));

        memberGroups.forEach(
                memberGroup -> memberGroupRepository.delete(memberGroup)
        );

        groupRepository.deleteByGroupId(groupId);

    }

}
