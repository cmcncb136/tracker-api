package com.kesi.tracker.track.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackAssignmentJpaRepository extends JpaRepository<TrackAssignmentEntity, Long> {
    List<TrackAssignmentEntity> findByTrackId(Long trackId);
}
