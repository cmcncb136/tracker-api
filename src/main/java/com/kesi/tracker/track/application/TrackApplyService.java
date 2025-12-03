package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.Track;

public interface TrackApplyService {
    void applyTrack(Long currentUserId, Long trackId);
    void cancleTrack(Long currentUserId, Long trackId);
}
