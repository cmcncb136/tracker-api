package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.application.repository.GroupMemberRepository;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.group.domain.GroupMemberStatus;
import com.kesi.tracker.group.domain.GroupRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GroupMemberRepositoryImpl implements GroupMemberRepository {
    private final GroupMemberJpaRepository groupMemberJpaRepository;

    @Override
    public GroupMember save(GroupMember groupMember) {
        return groupMemberJpaRepository.save(GroupMemberEntity.fromDomain(groupMember))
                .toDomain();
    }

    @Override
    public Optional<GroupMember> findById(Long id) {
        return groupMemberJpaRepository.findById(id).map(GroupMemberEntity::toDomain);
    }

    @Override
    public Optional<GroupMember> findByGidAndUid(Long gid, Long uid) {
        return groupMemberJpaRepository.findByGidAndUid(gid, uid).map(GroupMemberEntity::toDomain);
    }

    @Override
    public List<GroupMember> findByGid(Long gid) {
        return groupMemberJpaRepository.findByGid(gid)
                .stream().map(GroupMemberEntity::toDomain).toList();
    }

    @Override
    public List<GroupMember> findByUid(Long uid) {
        return groupMemberJpaRepository.findByUid(uid)
                .stream().map(GroupMemberEntity::toDomain).toList();
    }

    @Override
    public List<GroupMember> findByGidAndStatus(Long gid, GroupMemberStatus status) {
        return groupMemberJpaRepository.findByGidAndStatus(gid, status)
                .stream().map(GroupMemberEntity::toDomain).toList();
    }

    @Override
    public List<GroupMember> findByUidAndStatus(Long uid, GroupMemberStatus status) {
        return groupMemberJpaRepository.findByUidAndStatus(uid, status)
                .stream().map(GroupMemberEntity::toDomain).toList();
    }

    @Override
    public List<GroupMember> findByGidAndRole(Long gid, GroupRole role) {
        return groupMemberJpaRepository.findByGidAndRole(gid, role)
                .stream().map(GroupMemberEntity::toDomain).toList();
    }

    @Override
    public boolean existsByGidAndUid(Long gid, Long uid) {
        return groupMemberJpaRepository.existsByGidAndUid(gid, uid);
    }
}
