package com.kesi.tracker.group.application.dto;

import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GroupProfileResponse {
    private String name;
    private String introduction;

    private UserProfileResponse creator;
    private LocalDateTime creationDate;
    private Integer memberCount;

    private List<String> profileImageUrls;
}
