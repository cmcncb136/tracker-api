package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.Group;

import java.util.List;

public interface GroupService {
    Group save(Group group, Long currentUserId);
    Group getById(Long id);
    List<Group> searchByName(String name);
    List<Group> searchByDescription(String description);

    void join(Long groupId, Long currentUserId);
    void invite(Long groupId, Long currentUserId);

    void registerHost(Long groupId, Long currentUserId, Long registerUserId);
    void unregisterHost(Long groupId, Long currentUserId, Long unregisterUserId);
}