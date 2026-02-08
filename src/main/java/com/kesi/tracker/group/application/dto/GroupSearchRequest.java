package com.kesi.tracker.group.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "그룹 검색 요청")
@Data
public class GroupSearchRequest {
    private String groupName;
}