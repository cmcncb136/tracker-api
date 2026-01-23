package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.TrackAssignment;

public interface TrackAssignmentService {
    void applyTrackAssignment(Long currentUid, Long trackAssignmentId);
    void cancelTrackAssignment(Long currentUid, Long trackAssignmentId);
    TrackAssignment getById(Long trackAssignmentId);
    TrackAssignment save(TrackAssignment trackAssignment);
    void deleteById(Long trackAssignmentId);
}
