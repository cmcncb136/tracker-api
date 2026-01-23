package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.track.domain.TrackMember;

import java.util.List;
import java.util.Optional;

public interface TrackMemberRepository {
    TrackMember save(TrackMember trackMember);
    Optional<TrackMember> findById(Long id);
    List<TrackMember> findByTrackAssignmentId(Long trackAssignmentId);
    List<TrackMember> findByUid(Long uid);
    Optional<TrackMember> findByTrackAssignmentIdAndUid(Long trackAssignmentId, Long uid);
    void deleteById(Long id);
}
