package com.kesi.tracker.track.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackMemberJpaRepository extends JpaRepository<TrackMemberEntity, Long> {
    Optional<TrackMemberEntity> findByTrackAssignmentIdAndUid(Long trackAssignmentId, Long uid);
    List<TrackMemberEntity> findByTrackAssignmentId(Long trackAssignmentId);
    List<TrackMemberEntity> findByUid(Long uid);
}
