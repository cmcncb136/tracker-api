package com.kesi.tracker.track.application.dto;

import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "그룹 정보가 포함된 트랙 응답")
@Data
@Builder
public class TrackWithGroupResponse {
    private TrackResponse track;
    private GroupProfileResponse group;
}
