package com.kesi.tracker.track.application;


public interface TrackApplyService {
    void applyTrack(Long currentUid, Long trackId);
    void cancelTrack(Long currentUid, Long trackId);
}
