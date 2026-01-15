package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.domain.GroupMemberStatus;
import com.kesi.tracker.group.domain.GroupRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberJpaRepository extends JpaRepository<GroupMemberEntity, Long> {
    List<GroupMemberEntity> findByGid(Long gid);
    Optional<GroupMemberEntity> findByGidAndUid(Long gid, Long uid);
    List<GroupMemberEntity> findByUid(Long uid);
    List<GroupMemberEntity> findByGidAndStatus(Long gid, GroupMemberStatus status);
    List<GroupMemberEntity> findByGidAndRole(Long gid, GroupRole role);
    boolean existsByGidAndUid(Long gid, Long uid);
}
