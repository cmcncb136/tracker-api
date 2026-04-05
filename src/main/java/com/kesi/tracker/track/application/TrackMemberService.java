package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.TrackMember;
import com.kesi.tracker.track.domain.TrackRole;

import java.util.List;

public interface TrackMemberService {
    TrackMember save(TrackMember trackMember);
    TrackMember getTrackMemberByTrackIdAndUid(Long trackId, Long uid);
    List<TrackMember> findByTrackId(Long trackId);
    List<TrackMember> findByTrackIdAndRole(Long trackId, TrackRole role);
    void deleteById(Long id);
}
