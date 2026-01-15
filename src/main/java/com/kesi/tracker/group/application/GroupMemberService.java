package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.GroupMember;

import java.util.List;

public interface GroupMemberService {
    GroupMember getApprovedByGidAndUid(Long gid, Long uid);
    GroupMember getByGidAndUid(Long gid, Long uid);
    List<GroupMember> findByGidAndRoleIsLeader(Long gid);

    GroupMember createInviteMember(Long gid, Long invitedUid);
    boolean existsGroupMember(Long gid, Long uid);
    boolean isGroupMember(Long gid, Long uid);
    boolean isGroupLeader(Long gid, Long uid);
}
