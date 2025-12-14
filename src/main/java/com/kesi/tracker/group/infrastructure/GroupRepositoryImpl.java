package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.application.repository.GroupRepository;
import com.kesi.tracker.group.domain.AccessType;
import com.kesi.tracker.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    private final GroupJpaRepository groupJpaRepository;


    @Override
    public List<Group> searchByName(String name) {
        return groupJpaRepository.searchByName(name)
                .stream().map(GroupEntity::toDomain).toList();
    }

    @Override
    public List<Group> findByNameContainingIgnoreCaseAndAccess(String name, AccessType access) {
        return groupJpaRepository.findByNameContainingIgnoreCaseAndAccess(name,access)
                .stream().map(GroupEntity::toDomain).toList();
    }

    @Override
    public Optional<Group> findByGid(Long gid) {
        return groupJpaRepository.findById(gid).map(GroupEntity::toDomain);
    }

    @Override
    public List<Group> findAll() {
        return groupJpaRepository.findAll()
                .stream().map(GroupEntity::toDomain).toList();
    }

    @Override
    public List<Group> findByUserId(Long userId) {
        return groupJpaRepository.findByUid(userId)
                .stream().map(GroupEntity::toDomain).toList();
    }

    @Override
    public Group save(Group group) {
        return groupJpaRepository.save(GroupEntity.fromDomain(group)).toDomain();
    }

    @Override
    public void deleteByGid(Long gid) {
        groupJpaRepository.deleteById(gid);
    }
}
