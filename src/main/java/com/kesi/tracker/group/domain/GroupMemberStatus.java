package com.kesi.tracker.group.domain;

import com.kesi.tracker.user.domain.ActionActor;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum GroupMemberStatus {
    REQUESTED,  // 가입 요청
    INVITED,    // 초대 요청
    APPROVED,   // 승인됨 (정식 멤버)
    REJECTED,   // 거절됨
    BLOCKED,    // 그룹에서 차단됨
    LEFT;        // 탈퇴


    private static final Map<ActionActor, Set<GroupMemberStatus>> PERMISSIONS = Map.of(
            ActionActor.LEADER, EnumSet.of(APPROVED, REJECTED, BLOCKED),
            ActionActor.MEMBER, EnumSet.of(LEFT, APPROVED), // APPROVED는 초대 수락 시
            ActionActor.SYSTEM, EnumSet.of(BLOCKED, LEFT)   // 시스템은 강제 처리 가능
    );

    public boolean canBeChangedBy(ActionActor actor) {
        return PERMISSIONS.getOrDefault(actor, Collections.emptySet()).contains(this);
    }
}
