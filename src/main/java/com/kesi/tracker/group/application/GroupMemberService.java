package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.GroupMember;

import java.util.List;

public interface GroupMemberService {
    GroupMember getApprovedByGidAndUid(Long gid, Long uid);
    GroupMember getByGidAndUid(Long gid, Long uid);
    List<GroupMember> findByGidAndRoleIsLeader(Long gid);
}
