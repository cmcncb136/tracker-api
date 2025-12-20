package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.group.domain.GroupRole;

import java.util.List;

public interface GroupMemberService {
    GroupMember getApprovedByGidAndUid(Long gid, Long uid);
    GroupMember getByGidAndUid(Long gid, Long uid);
    List<GroupMember> findByGidAndRole(Long gid, GroupRole role);
}
