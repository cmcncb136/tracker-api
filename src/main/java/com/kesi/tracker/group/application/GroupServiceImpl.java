package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.application.repository.GroupRepository;
import com.kesi.tracker.group.domain.*;
import com.kesi.tracker.group.domain.event.GroupMemberInviteRequestedEvent;
import com.kesi.tracker.group.domain.event.GroupMemberInvitedEvent;
import com.kesi.tracker.group.domain.event.GroupTrackRoleChangedEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getByGid(Long gid) {
        return groupRepository.findByGid(gid).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @Override
    public List<Group> searchPublicByName(String name) {
        return groupRepository.findByNameContainingIgnoreCaseAndAccess(name, AccessType.PUBLIC);
    }

    @Override
    public void join(Long groupId, Long currentUserId) {
        GroupMember groupMember = groupMemberRepository.findByGidAndUid(groupId, currentUserId)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        groupMember.approve();

        groupMemberRepository.save(groupMember);
    }

    @Override
    @Transactional
    public void invite(Long groupId, Email invitedUserEmail, Long currentUserId) {
        GroupMember currentGroupMember
                = groupMemberRepository.findByGidAndUid(groupId, currentUserId)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        User invitedUser = userService.getByEmail(invitedUserEmail);

        if (!currentGroupMember.isLeader()) throw new RuntimeException("초대는 리더만 할 수 있습니다");

        if (groupMemberRepository.findByGidAndUid(currentGroupMember.getGid(), invitedUser.getId()).isPresent())
            throw new RuntimeException("이미 그룹 멤버이거나 초대가 진행 중입니다."); //Todo. block 등의 경우 보완 로직 필요


        GroupMember groupMember = GroupMember.builder()
                .gid(groupId)
                .uid(invitedUser.getId())
                .role(GroupRole.MEMBER)
                .trackRole(GroupTrackRole.FOLLOWER)
                .status(GroupMemberStatus.INVITED)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        groupMemberRepository.save(groupMember);

        applicationEventPublisher.publishEvent(
                GroupMemberInvitedEvent.builder()
                        .invitedUserId(invitedUser.getId())
                        .inviterId(currentUserId)
                        .gid(groupId)
                        .build());
    }

    @Override
    @Transactional
    public void request(Long groupId, Long currentUserId) {
        List<GroupMember> leaderGroupMembers = groupMemberRepository.findByGidAndRole(groupId, GroupRole.LEADER);

        if (leaderGroupMembers.isEmpty()) {
            log.error("해당 그룹에 리더가 존재하지 않습니다..? groupId : {}", groupId);
        }

        GroupMember groupMember = GroupMember.builder()
                .gid(groupId)
                .uid(currentUserId)
                .role(GroupRole.MEMBER)
                .trackRole(GroupTrackRole.FOLLOWER)
                .status(GroupMemberStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        groupMemberRepository.save(groupMember);

        applicationEventPublisher.publishEvent(
                GroupMemberInviteRequestedEvent.builder()
                        .groupId(groupId)
                        .leaderIds(leaderGroupMembers.stream().map(GroupMember::getUid).collect(Collectors.toList()))
                        .requestedUserId(currentUserId)
                        .build()
        );
    }

    @Override
    @Transactional
    public void registerHost(Long gid, Long currentUid, Long registerUid) {
        changeTrackRole(gid, currentUid, registerUid, GroupTrackRole.HOST);
    }

    @Override
    @Transactional
    public void registerFollower(Long gid, Long currentUid, Long unregisterUid) {
        changeTrackRole(gid, currentUid, unregisterUid, GroupTrackRole.FOLLOWER);
    }

    private void changeTrackRole(Long gid, Long currentUid, Long targetUid, GroupTrackRole trackRole) {
        GroupMember currentGroupMember = groupMemberRepository.findByGidAndUid(gid, currentUid)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        //리더만 Track Role를 지정할 수 있다
        if (!currentGroupMember.isLeader()) throw new RuntimeException("GroupMember is not leader");

        GroupMember registerGroupMember = groupMemberRepository.findByGidAndUid(gid, targetUid)
                .orElseThrow(() -> new RuntimeException("target GroupMember not found"));

        if (registerGroupMember.getTrackRole() == trackRole) return;

        registerGroupMember.setTrackRole(trackRole);
        groupMemberRepository.save(registerGroupMember);

        applicationEventPublisher.publishEvent(
                GroupTrackRoleChangedEvent.builder()
                .groupId(gid)
                .changedRole(trackRole)
                .roleChangedUserId(registerGroupMember.getUid())
                .build()
        );
    }
}
