package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.dto.*;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.user.domain.Email;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GroupService {
    Group create(Group group);
    Group getByGid(Long gid);
    Page<Group> searchPublicByName(String name, Pageable pageable);

    void join(Long groupId, Long currentUid);
    void invite(Long groupId, Email invitedUserEmail, Long currentUid);
    void joinRequest(Long groupId, Long currentUid);

    void registerHost(Long groupId, Long currentUid, Email targetUserEmail);
    void registerFollower(Long groupId, Long currentUid, Email targetUserEmail);

    GroupResponse getGroupResponseByGid(Long gid, @Nullable Long currentUid);
    List<GroupProfileResponse> getGroupResponseByUid(Long uid);
    Map<Long, GroupProfileResponse> getGroupResponseByGids(List<Long> gids);
    Map<Long, GroupProfileResponse> getGroupResponseByGids(Set<Long> gids);
    Page<GroupProfileResponse> searchPublicGroups(GroupSearchRequest searchRequest, Pageable pageable);
    GroupResponse create(GroupCreationRequest groupCreationRequest, Long currentUid);
}