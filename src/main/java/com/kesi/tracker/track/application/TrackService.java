package com.kesi.tracker.track.application;

import com.kesi.tracker.track.domain.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    Track createTrack(Track track);
    Track updateTrack(Track track);
    Optional<Track> findByTrack(Long id);
    List<Track> findByGid(Long gid);
}
