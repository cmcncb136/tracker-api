package com.kesi.tracker.group.application.repository;

import com.kesi.tracker.group.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> searchByName(String name);
    List<Group> searchPublicByName(String name);
    Optional<Group> findByGid(Long gid);
    List<Group> findAll();
    List<Group> findByUserId(Long userId);

    Group save(Group group);
    void deleteByGid(Long gid);
}
