package com.kesi.tracker.group.application;

import com.kesi.tracker.group.application.dto.GroupCreationRequest;
import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import com.kesi.tracker.group.application.dto.GroupResponse;
import com.kesi.tracker.group.application.dto.GroupSearchRequest;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.user.domain.Email;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
    Group create(Group group);
    Group getByGid(Long gid);
    Page<Group> searchPublicByName(String name, Pageable pageable);

    void join(Long groupId, Long currentUid);
    void invite(Long groupId, Email invitedUserEmail, Long currentUid);
    void request(Long groupId, Long currentUid);

    void registerHost(Long groupId, Long currentUid, Long registerUid);
    void registerFollower(Long groupId, Long currentUid, Long unregisterUid);

    GroupResponse getGroupResponseByGid(Long gid, @Nullable Long currentUid);
    Page<GroupProfileResponse> searchPublicGroups(GroupSearchRequest searchRequest, Pageable pageable);
    GroupResponse create(GroupCreationRequest groupCreationRequest, Long currentUid);
}