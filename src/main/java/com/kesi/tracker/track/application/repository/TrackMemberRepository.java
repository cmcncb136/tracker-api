package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.group.domain.GroupTrackRole;
import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.TrackRole;

import java.util.List;
import java.util.Optional;

public interface TrackMemberRepository {
    TrackMember save(TrackMember trackMember);
    Optional<TrackMember> findById(Long id);
    Optional<TrackMember> findByTrackIdAndUid(Long trackId, Long uid);
    List<TrackMember> findByTrackId(Long trackId);
    List<TrackMember> findByTrackIdAndRole(Long trackId, TrackRole role);
    List<TrackMember> findByUid(Long uid);
    void deleteById(Long id);
}
