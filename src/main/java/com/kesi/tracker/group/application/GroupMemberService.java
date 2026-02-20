package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.GroupMember;

import java.util.List;

public interface GroupMemberService {
    GroupMember getApprovedByGidAndUid(Long gid, Long uid);
    GroupMember getByGidAndUid(Long gid, Long uid);
    GroupMember getById(Long id);
    List<GroupMember> findByGidAndRoleIsLeader(Long gid);

    GroupMember createInviteMember(Long gid, Long invitedUid);
    GroupMember createLeaderMember(Long gid, Long leaderUid);
    boolean existsGroupMember(Long gid, Long uid);
    boolean isGroupMember(Long gid, Long uid);
    boolean isGroupLeader(Long gid, Long uid);
}
