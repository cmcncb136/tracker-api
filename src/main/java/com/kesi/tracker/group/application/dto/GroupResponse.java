package com.kesi.tracker.group.application.dto;

import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GroupResponse {
    private String name;
    private String introduction;
    private String description;

    private UserProfileResponse creator;
    private LocalDateTime creationDate;
    private Integer groupMemberNumber;
}
