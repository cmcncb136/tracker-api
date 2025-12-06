package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.track.domain.TrackMember;

import java.util.List;
import java.util.Optional;

public interface TrackMemberRepository {
    TrackMember save(TrackMember trackMember);
    Optional<TrackMember> findById(Long id);
    Optional<TrackMember> findByTrackIdAndUid(Long trackId, Long uid);
    List<TrackMember> findByTrackId(Long trackId);
    List<TrackMember> findByUid(Long uid);
    void deleteById(Long id);
}
