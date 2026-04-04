package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.TrackMember;

public interface TrackMemberService {
    TrackMember save(TrackMember trackMember);
    TrackMember getTrackMemberByTrackIdAndUid(Long trackId, Long uid);
    void deleteById(Long id);
}
