package com.kesi.tracker.group.application.repository;

import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.group.domain.GroupMemberStatus;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository {
    GroupMember save(GroupMember groupMember);
    Optional<GroupMember> findById(Long id);
    Optional<GroupMember> findByGidAndUid(Long gid, Long uid);
    List<GroupMember> findByGid(Long gid);
    List<GroupMember> findByUid(Long uid);
    List<GroupMember> findByGidAndStatus(Long gid, GroupMemberStatus status);
}
