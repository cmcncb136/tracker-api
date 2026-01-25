package com.kesi.tracker.track.application.dto;

import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackWithGroupResponse {
    private TrackResponse track;
    private GroupProfileResponse group;
}
