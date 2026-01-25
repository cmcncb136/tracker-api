package com.kesi.tracker.track.application.repository;

import com.kesi.tracker.track.application.query.TrackSearchCondition;
import com.kesi.tracker.track.application.query.TrackWithGroupSearchCondition;
import com.kesi.tracker.track.domain.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TrackRepository {
    List<Track> findAll();
    List<Track> findByGid(Long gid);
    List<Track> findByUserId(Long userId);
    Optional<Track> findById(Long id);
    List<Track> searchByTitleAndGid(String title, Long gid);
    Track save(Track track);
    Page<Track> searchInGroup(Long gid, TrackSearchCondition condition, Pageable pageable);
    Page<Track> searchInGroupInUser(Long uid, TrackWithGroupSearchCondition condition, Pageable pageable);
}
