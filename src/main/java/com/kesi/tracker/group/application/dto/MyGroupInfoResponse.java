package com.kesi.tracker.group.application.dto;

import com.kesi.tracker.group.domain.GroupRole;
import com.kesi.tracker.group.domain.GroupTrackRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "내 그룹에서 정보 응답")
@Builder
@Data
public class MyGroupInfoResponse {
    @Schema(description = "그룹 역할")
    GroupRole role;
    @Schema(description = "트랙 역할 (HOST, FOLLOWER)")
    GroupTrackRole trackRole;
}
