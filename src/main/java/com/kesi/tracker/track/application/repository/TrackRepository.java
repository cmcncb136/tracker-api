package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.track.domain.Track;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {
    List<Track> findAll();

    List<Track> findByGid(Long gid);
    List<Track> findByUserId(Long userId);
    Optional<Track> findById(Long id);

    List<Track> searchByTitleAndGid(String title, Long gid);
}
