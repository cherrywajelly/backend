package com.timeToast.timeToast.service.member_group;

import com.timeToast.timeToast.domain.member_group.MemberGroup;
import com.timeToast.timeToast.domain.group_member.GroupMember;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.member_group.request.MemberGroupSaveRequest;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponse;
import com.timeToast.timeToast.dto.member_group.response.MemberGroupResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.group_member.GroupMemberRepository;
import com.timeToast.timeToast.repository.member_group.MemberGroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
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

    private final MemberGroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    public MemberGroupServiceImpl(final MemberGroupRepository groupRepository, final MemberRepository memberRepository,
                                  final GroupMemberRepository memberGroupRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.groupMemberRepository = memberGroupRepository;
    }

    @Transactional
    @Override
    public MemberGroupResponse saveGroup(final long memberId, final MemberGroupSaveRequest groupSaveRequest) {

        //save group
        MemberGroup memberGroup = MemberGroup.builder()
                .name(groupSaveRequest.groupName())
                .build();

        MemberGroup saveMemberGroup = groupRepository.save(memberGroup);

        //save member_group
        groupMemberRepository.save(
                GroupMember.builder()
                        .groupId(saveMemberGroup.getId())
                        .memberId(memberId)
                        .build()
        );

        List<Long> groupMembers = groupSaveRequest.groupMembers();

        groupMembers.forEach(
                (groupMemberId) -> {
                    Member findMember = memberRepository.findById(groupMemberId).orElseThrow( () -> new BadRequestException(MEMBER_NOT_FOUND.getMessage()));

                    groupMemberRepository.save(
                            GroupMember.builder()
                                    .groupId(saveMemberGroup.getId())
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

        MemberGroup memberGroup = groupRepository.findById(groupId).orElseThrow(()->
                new NotFoundException(GROUP_NOT_FOUND.getMessage())
        );

        memberGroup.updateGroupProfileUrl(groupProfileUrl);

        return MemberGroupResponse.from(memberGroup);
    }

    @Transactional
    @Override
    public MemberGroupResponses findLoginMemberGroups(final long memberId) {

        List<GroupMember> groupMembers = groupMemberRepository.findAllByMemberId(memberId);
        List<MemberGroupResponse> memberGroupRespons = new ArrayList<>();

        groupMembers.forEach(
                memberGroup -> {
                    Optional<MemberGroup> findGroup= groupRepository.findById(memberGroup.getGroupId());
                    if(findGroup.isPresent()){
                        memberGroupRespons.add(
                                MemberGroupResponse.from(findGroup.get())
                        );
                    }

                }
        );

        return new MemberGroupResponses(memberGroupRespons);
    }

    @Transactional
    @Override
    public void deleteMemberGroup(final long memberId, final long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(groupId);

        groupMembers.stream()
                        .filter((memberGroup -> memberGroup.getMemberId() == memberId))
                        .findFirst().orElseThrow(()-> new NotFoundException(MEMBER_GROUP_NOT_FOUND.getMessage()));

        groupMembers.forEach(
                memberGroup -> groupMemberRepository.delete(memberGroup)
        );

        groupRepository.deleteByGroupId(groupId);

    }

}
