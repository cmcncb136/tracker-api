package com.kesi.tracker.group.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GroupMember {
    Long id;
    Long gid;
    Long uid;
    GroupMemberStatus status;
    GroupRole role;
    @Setter
    GroupTrackRole trackRole;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public void approve() {
        if(status != GroupMemberStatus.REQUESTED && status != GroupMemberStatus.APPROVED)
            throw new IllegalStateException("승인할 수 있는 상태가 아닙니다");

        status = GroupMemberStatus.APPROVED;
    }

    public boolean isLeader() {
        return role == GroupRole.LEADER;
    }

    public boolean isApproved() {
        return status == GroupMemberStatus.APPROVED;
    }

    public boolean isHost() {
        return trackRole == GroupTrackRole.HOST;
    }

    public boolean isFollower() {
        return trackRole == GroupTrackRole.FOLLOWER;
    }

}
