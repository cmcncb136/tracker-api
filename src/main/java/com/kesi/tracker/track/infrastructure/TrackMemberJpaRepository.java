package com.kesi.tracker.track.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackMemberJpaRepository extends JpaRepository<TrackMemberEntity, Long> {
    Optional<TrackMemberEntity> findByTrackIdAndUid(Long trackId, Long uid);
    List<TrackMemberEntity> findByTrackId(Long trackId);
    List<TrackMemberEntity> findByUid(Long uid);
}
