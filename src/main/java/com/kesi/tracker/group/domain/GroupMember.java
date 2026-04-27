package com.kesi.tracker.group.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.user.domain.ActionActor;
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


    public MemberCountChange changeStatusByLeader(GroupMemberStatus status, ActionActor actionActor) {
        GroupMemberStatus beforeStatus = this.status;

        if(!status.canBeChangedBy(actionActor))
            throw new BusinessException(ErrorCode.UNAUTHORIZED_STATUS_CHANGE);

        switch (status) {
            case APPROVED -> {
                if (ActionActor.MEMBER.equals(actionActor))
                    this.acceptInvitation();
                else this.approve();
            }
            case BLOCKED -> this.block();
            case REJECTED -> this.reject();
            case REQUESTED -> this.unblock();
            case LEFT -> this.left();
            default -> throw new BusinessException(ErrorCode.INVALID_MEMBER_STATUS);
        }

        return MemberCountChange.from(beforeStatus, this.status);
    }

    public void unblock() {
        if(this.status != GroupMemberStatus.BLOCKED)
            throw new BusinessException(ErrorCode.INVALID_MEMBER_STATUS);

        this.status = GroupMemberStatus.REQUESTED;
    }

    private void block() {
        if(isLeader()) { //방의 리더는 자기 자신을 차단할 수 없음
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }

        status = GroupMemberStatus.BLOCKED;
    }

    private void reject() {
        //요청 또는 초대 상태인 경우만 거절 가능
        if(status != GroupMemberStatus.REQUESTED && status != GroupMemberStatus.INVITED)
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);

        status = GroupMemberStatus.REJECTED;
    }


    private void approve() {
        if(status != GroupMemberStatus.REQUESTED)
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);

        status = GroupMemberStatus.APPROVED;
    }

    private void acceptInvitation() {
        if(status != GroupMemberStatus.INVITED)
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);

        status = GroupMemberStatus.APPROVED;
    }

    private void left() {
        //Todo. 추후 구현 예정
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
