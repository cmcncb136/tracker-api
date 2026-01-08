package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.user.domain.Email;

import java.util.List;

public interface GroupService {
    Group create(Group group);
    Group getByGid(Long gid);
    List<Group> searchPublicByName(String name);

    void join(Long groupId, Long currentUid);
    void invite(Long groupId, Email invitedUserEmail, Long currentUid);
    void request(Long groupId, Long currentUid);

    void registerHost(Long groupId, Long currentUid, Long registerUid);
    void registerFollower(Long groupId, Long currentUid, Long unregisterUid);
}