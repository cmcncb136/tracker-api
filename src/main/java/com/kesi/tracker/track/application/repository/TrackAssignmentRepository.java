package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.track.domain.TrackAssignment;

import java.util.List;
import java.util.Optional;

public interface TrackAssignmentRepository {
    TrackAssignment save(TrackAssignment trackAssignment);
    Optional<TrackAssignment> findById(Long id);
    List<TrackAssignment> findByTrackId(Long trackId);
    void deleteById(Long id);
}
