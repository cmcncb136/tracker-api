package com.kesi.tracker.track.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrackWithAssignmentResponse {
    private TrackResponse track;
    private List<TrackAssignmentResponse> assignment;
}
