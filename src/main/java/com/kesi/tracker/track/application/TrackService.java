package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.Track;

import java.util.List;

public interface TrackService {
    Track createTrack(Track track, Long currentUid);
    Track updateTrack(Track track, Long currentUid);
    Track getById(Long id);
    List<Track> findByGid(Long gid);
}
