package com.kesi.tracker.track.application;

import com.kesi.tracker.track.application.dto.*;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.user.application.dto.TrackMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrackService {
    Track create(Track track, Long currentUid);
    Track getById(Long id);
    List<Track> findByGid(Long gid);
    List<TrackMemberResponse> findUserProfileByTrackIdAndRole(Long trackId, TrackRoleFilter roleFilter, Long currentUid);
    TrackResponse getTrackResponseById(Long id, Long currentUid);
    Page<TrackResponse> searchTrackInGroup(Long gid, Long currentUid, TrackSearchRequest searchRequest, Pageable pageable);
    Page<TrackWithGroupResponse> searchTrackInGroupInUser(Long currentUid, TrackWithGroupSearchRequest searchRequest, Pageable pageable);
    TrackResponse create(Long gid, TrackCreationRequest track, Long currentUid);
    TrackResponse update(Long trackId, TrackUpdateRequest track, Long currentUid);

}
