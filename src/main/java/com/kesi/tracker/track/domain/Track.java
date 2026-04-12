package com.kesi.tracker.track.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Track {
    private Long id;
    private Long gid;
    private Long hostId;

    private int capacity; //최대 수용인원
    private int followerCnt;

    private String title;
    private String introduce;
    private String description;
    private String place;
    private Long cost;

    //트랙 운영 기간 (수강 신청 기간 포함)
    private LocalDateTime operatingStartAt;
    private LocalDateTime operatingEndAt;

    //수강 신청 기간
    private LocalDateTime assignmentStartAt;
    private LocalDateTime assignmentEndAt;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createdBy;
    private Long modifiedBy;

    @Builder
    public Track(Long id, Long gid, Long hostId, int capacity, int followerCnt, String title, String introduce, String description, String place, Long cost, LocalDateTime operatingStartAt, LocalDateTime operatingEndAt, LocalDateTime assignmentStartAt, LocalDateTime assignmentEndAt, LocalDateTime createdAt, LocalDateTime modifiedAt, Long createdBy, Long modifiedBy) {
        this.id = id;
        this.gid = gid;
        this.hostId = hostId;
        this.capacity = capacity;
        this.followerCnt = followerCnt;
        this.title = title;
        this.introduce = introduce;
        this.description = description;
        this.place = place;
        this.cost = cost;
        this.operatingStartAt = operatingStartAt;
        this.operatingEndAt = operatingEndAt;
        this.assignmentStartAt = assignmentStartAt;
        this.assignmentEndAt = assignmentEndAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;

        if(!isOperatingWithinPeriod(assignmentStartAt) || !isOperatingWithinPeriod(assignmentEndAt)) {
            throw new BusinessException(ErrorCode.INVALID_RECRUITMENT_PERIOD);
        }
    }

    public boolean isAssignmentWithinPeriod(LocalDateTime target) {
        return !target.isBefore(assignmentStartAt) && !target.isAfter(assignmentEndAt);
    }

    public boolean isOperatingWithinPeriod(LocalDateTime target) {
        return !target.isBefore(operatingStartAt) && !target.isAfter(operatingEndAt);
    }

}