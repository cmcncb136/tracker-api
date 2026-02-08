package com.kesi.tracker.group.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
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
            throw new BusinessException(ErrorCode.CANNOT_APPROVE_STATE);

        status = GroupMemberStatus.APPROVED;
    }

    public void acceptInvitation() {
        if(status != GroupMemberStatus.INVITED)
            throw new BusinessException(ErrorCode.NOT_INVITED_STATE);

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
