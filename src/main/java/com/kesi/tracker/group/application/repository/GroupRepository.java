package com.kesi.tracker.group.application.repository;

import com.kesi.tracker.group.domain.AccessType;
import com.kesi.tracker.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<Group> searchByName(String name);
    Page<Group> findByNameContainingIgnoreCaseAndAccess(String name, AccessType access, Pageable pageable);
    Optional<Group> findByGid(Long gid);
    List<Group> findAll();
    List<Group> findByUserId(Long userId);

    Group save(Group group);
    void deleteByGid(Long gid);
}
