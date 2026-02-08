package com.kesi.tracker.group.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Group {
    private Long gid;
    private String name;
    private String introduce;
    private String description;

    private AccessType access;
    private Long createdBy;
    private LocalDateTime createdAt;

    private Integer memberCount;

    public boolean isPrivate() {
        return access == AccessType.PRIVATE;
    }

    public boolean isPublic() {
        return access == AccessType.PUBLIC;
    }

    public void increaseMemberCount() {
        memberCount++;
    }

    public void decreaseMemberCount() {
        if(memberCount <= 0) {
            throw new BusinessException(ErrorCode.MEMBER_COUNT_UNDERFLOW);
        }

        memberCount--;
    }
}
