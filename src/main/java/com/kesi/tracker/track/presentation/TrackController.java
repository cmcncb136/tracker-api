package com.kesi.tracker.track.presentation;


import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.track.application.TrackService;
import com.kesi.tracker.track.application.dto.TrackResponse;
import com.kesi.tracker.track.application.dto.TrackSearchRequest;
import com.kesi.tracker.track.application.dto.TrackWithGroupResponse;
import com.kesi.tracker.track.application.dto.TrackWithGroupSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;

    @GetMapping("/groups/tracks/{trackId}")
    public TrackResponse getTrack(
            @PathVariable("trackId") Long trackId,
            @UserId Long userId
            ) {

        return trackService.getTrackResponseById(trackId, userId);
    }

    @GetMapping("/groups/tracks")
    public Page<TrackResponse> searchTrackInGroup(
            @UserId Long userId,
            @RequestParam Long gid,
            @ModelAttribute TrackSearchRequest searchRequest,
            @PageableDefault Pageable pageable
            ) {
        return trackService.searchTrackInGroup(gid, userId, searchRequest, pageable);
    }

    @GetMapping("/users/tracks")
    public Page<TrackWithGroupResponse> searchTrackInUser(
            @UserId Long userId,
            @ModelAttribute TrackWithGroupSearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        return trackService.searchTrackInGroupInUser(userId, searchRequest, pageable);
    }
}
