package com.kesi.tracker.group.application;

import com.kesi.tracker.group.domain.GroupMember;

public interface GroupMemberService {
    GroupMember getApprovedByGidAndUid(Long gid, Long uid);
    GroupMember getByGidAndUid(Long gid, Long uid);
}
