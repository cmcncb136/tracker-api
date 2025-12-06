package com.kesi.tracker.group.domain;

public enum GroupMemberStatus {
    REQUESTED,  // 가입 요청
    INVITED,    // 초대 요청
    APPROVED,   // 승인됨 (정식 멤버)
    REJECTED,   // 거절됨
    BLOCKED,    // 그룹에서 차단됨
    LEFT        // 탈퇴
}
