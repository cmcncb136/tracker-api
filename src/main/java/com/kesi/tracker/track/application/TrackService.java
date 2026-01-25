package com.kesi.tracker.track.application;

import com.kesi.tracker.track.application.dto.TrackResponse;
import com.kesi.tracker.track.application.dto.TrackSearchRequest;
import com.kesi.tracker.track.application.dto.TrackWithGroupResponse;
import com.kesi.tracker.track.application.dto.TrackWithGroupSearchRequest;
import com.kesi.tracker.track.domain.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrackService {
    Track createTrack(Track track, Long currentUid);
    Track updateTrack(Track track, Long currentUid);
    Track getById(Long id);
    List<Track> findByGid(Long gid);
    TrackResponse getTrackResponseById(Long id, Long currentUid);
    Page<TrackResponse> searchTrackInGroup(Long gid, Long currentUid, TrackSearchRequest searchRequest, Pageable pageable);
    Page<TrackWithGroupResponse> searchTrackInGroupInUser(Long currentUid, TrackWithGroupSearchRequest searchRequest, Pageable pageable);
}
