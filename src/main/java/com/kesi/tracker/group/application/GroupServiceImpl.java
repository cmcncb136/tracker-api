package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.application.repository.GroupRepository;
import com.kesi.tracker.group.domain.*;
import com.kesi.tracker.group.domain.event.GroupMemberInvitedEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
    public void invite(Long groupId, String invitedUserEmail, Long currentUserId) {
        GroupMember currentGroupMember
                = groupMemberRepository.findByGidAndUid(groupId, currentUserId)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        User invitedUser = userService.getByEmail(invitedUserEmail);
        Group group = getByGid(groupId);

        if(!currentGroupMember.isLeader()) throw new RuntimeException("초대는 리더만 할 수 있습니다");

        if(groupMemberRepository.findByGidAndUid(currentGroupMember.getGid(), invitedUser.getId()).isPresent())
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
                        .groupName(group.getName())
                        .gid(groupId)
                .build());
    }

    @Override
    public void request(Long groupId, Long currentUserId) {
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
    }

    @Override
    public void registerHost(Long gid, Long currentUid, Long registerUid) {
        changeTrackRole(gid, currentUid, registerUid, GroupTrackRole.HOST);
    }

    @Override
    public void unregisterHost(Long gid, Long currentUid, Long unregisterUid) {
        changeTrackRole(gid, currentUid, unregisterUid, GroupTrackRole.FOLLOWER);
    }

    private void changeTrackRole(Long gid, Long currentUid, Long targetUid, GroupTrackRole trackRole) {
        GroupMember currentGroupMember = groupMemberRepository.findByGidAndUid(gid, currentUid)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        //리더만 TrackRole를 지정할 수 있다
        if (!currentGroupMember.isLeader()) throw new RuntimeException("GroupMember is not leader");

        GroupMember registerGroupMember = groupMemberRepository.findByGidAndUid(gid, targetUid)
                .orElseThrow(() -> new RuntimeException("target GroupMember not found"));

        registerGroupMember.setTrackRole(trackRole);
        groupMemberRepository.save(registerGroupMember);
    }
}
