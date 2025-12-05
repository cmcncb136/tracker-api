package com.kesi.tracker.group.application.repository;

import com.kesi.tracker.group.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> searchByName(String name);
    List<Group> searchByNameAndPublic(String name);
    Optional<Group> searchByGid(Long gid);
    List<Group> findAll();
    List<Group> findByUserId(Long userId);

    Group save(Group group);
    void deleteByGid(Long gid);
}
