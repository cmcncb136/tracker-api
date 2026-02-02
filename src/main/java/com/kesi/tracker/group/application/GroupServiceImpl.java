package com.kesi.tracker.group.application;

import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.group.application.dto.*;
import com.kesi.tracker.group.application.mapper.GroupMapper;
import com.kesi.tracker.file.domain.FileOwners;
import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.application.repository.GroupRepository;
import com.kesi.tracker.group.domain.*;
import com.kesi.tracker.group.domain.event.GroupMemberInviteRequestedEvent;
import com.kesi.tracker.group.domain.event.GroupMemberInvitedEvent;
import com.kesi.tracker.group.domain.event.GroupTrackRoleChangedEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserService userService;
    private final GroupMemberService groupMemberService;

    private final GroupMemberRepository groupMemberRepository;
    private final FileService fileService;


    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getByGid(Long gid) {
        return groupRepository.findByGid(gid).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @Override
    public Page<Group> searchPublicByName(String name, Pageable pageable) {
        return groupRepository.findByNameContainingIgnoreCaseAndAccess(name, AccessType.PUBLIC, pageable);
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
    public void invite(Long gid, Email invitedUserEmail, Long currentUserId) {
        if (!groupMemberService.isGroupLeader(gid, currentUserId)) throw new RuntimeException("초대는 리더만 할 수 있습니다");


        User invitedUser = userService.getByEmail(invitedUserEmail);


        if (groupMemberService.existsGroupMember(gid, currentUserId))
            throw new RuntimeException("이미 그룹 멤버이거나 초대가 진행 중입니다."); //Todo. block 등의 경우 보완 로직 필요


        groupMemberService.createInviteMember(gid, invitedUser.getId());

        applicationEventPublisher.publishEvent(
                GroupMemberInvitedEvent.builder()
                        .invitedUserId(invitedUser.getId())
                        .inviterId(currentUserId)
                        .gid(gid)
                        .build());
    }

    @Override
    @Transactional
    public void joinRequest(Long groupId, Long currentUserId) {
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
    public void registerHost(Long gid, Long currentUid, Email targetUserEmail) {
        changeTrackRole(gid, currentUid, targetUserEmail, GroupTrackRole.HOST);
    }

    @Override
    @Transactional
    public void registerFollower(Long gid, Long currentUid, Email targetUserEmail) {
        changeTrackRole(gid, currentUid, targetUserEmail, GroupTrackRole.FOLLOWER);
    }

    @Override
    public GroupResponse getGroupResponseByGid(Long gid, @Nullable Long currentUid) {
        Group group = getByGid(gid);

        if (group.isPrivate()) { //PRIVATE인 그룹인 경우
            if (!groupMemberService.isGroupMember(gid, currentUid)) //GROUP에 속한 경우만 확인 가능
                throw new IllegalArgumentException("Don't have permission to access this group");
        }

        return GroupMapper.toGroupResponse(
                group,
                userService.getProfile(group.getCreatedBy()),
                fileService.findAccessUrlByOwner(FileOwner.ofGroup(gid))
        );
    }

    @Override
    public List<GroupProfileResponse> getGroupResponseByUid(Long uid) {
        List<Group> groups = groupRepository.findByUid(uid);

        Map<Long, List<FileAccessUrl>> fileaccessurlsMap
                = fileService.findAccessUrlByOwners(FileOwners.ofGroup(groups.stream().map(Group::getGid).toList()));

        return groups.stream().map(group ->
                GroupMapper.toGroupProfileResponse(
                        group,
                        fileaccessurlsMap.getOrDefault(group.getGid(), Collections.emptyList())))
                .toList();
    }

    @Override
    public Map<Long, GroupProfileResponse> getGroupResponseByGids(List<Long> gids) {
        return this.getGroupResponseByGids(new HashSet<>(gids));
    }

    @Override
    public Map<Long, GroupProfileResponse> getGroupResponseByGids(Set<Long> gids) {
        List<Group> groups = groupRepository.findByGids(gids.stream().toList());
        if(groups.size() != gids.size())
            throw new RuntimeException("Group not found (request : " + gids.size() + ", found : " + gids.size() + ")");

        Map<Long, List<FileAccessUrl>> fileaccessurlsMap
                = fileService.findAccessUrlByOwners(FileOwners.ofGroup(groups.stream().map(Group::getGid).toList()));

        return groups.stream().collect(
                Collectors.toMap(
                        Group::getGid,
                        group -> GroupMapper.toGroupProfileResponse(
                                group,
                                fileaccessurlsMap.getOrDefault(group.getGid(), Collections.emptyList())
                        )
                )
        );
    }

    @Override
    public Page<GroupProfileResponse> searchPublicGroups(GroupSearchRequest searchRequest, Pageable pageable) {
        Page<Group> groups = this.searchPublicByName(searchRequest.getGroupName(), pageable);

        Map<Long, List<FileAccessUrl>> fileAccessUrlMap
                = fileService.findAccessUrlByOwners(FileOwners.ofGroup(groups.stream().map(Group::getGid).toList()));

        return groups.map(group ->
                GroupMapper.toGroupProfileResponse(
                        group,
                        fileAccessUrlMap.getOrDefault(group.getGid(), List.of()
                        )
                )
        );
    }

    @Override
    public GroupResponse create(GroupCreationRequest groupCreationRequest, Long currentUid) {
        Group group = GroupMapper.toGroup(groupCreationRequest, currentUid);

        Group savedGroup = groupRepository.save(group);
        FileOwner fileOwner = FileOwner.ofGroup(savedGroup.getGid());

        fileService.assignFileOwner(fileOwner, groupCreationRequest.getProfileFileIds());

        return GroupMapper.toGroupResponse(savedGroup,
                userService.getProfile(group.getCreatedBy()),
                fileService.findAccessUrlByOwner(fileOwner));
    }


    private void changeTrackRole(Long gid, Long currentUid, Email targetUserEmail, GroupTrackRole trackRole) {
        GroupMember currentGroupMember = groupMemberRepository.findByGidAndUid(gid, currentUid)
                .orElseThrow(() -> new RuntimeException("GroupMember not found"));

        //리더만 Track Role를 지정할 수 있다
        if (!currentGroupMember.isLeader()) throw new RuntimeException("GroupMember is not leader");

        User targetUser = userService.getByEmail(targetUserEmail);

        GroupMember registerGroupMember = groupMemberRepository.findByGidAndUid(gid, targetUser.getId())
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
