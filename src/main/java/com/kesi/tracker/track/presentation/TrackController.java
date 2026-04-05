package com.kesi.tracker.track.presentation;


import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.track.application.TrackAssignmentService;
import com.kesi.tracker.track.application.TrackService;
import com.kesi.tracker.track.application.dto.*;
import com.kesi.tracker.user.application.dto.TrackMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Track API", description = "트랙(스터디/강의) 관리 및 수강 신청")
@RestController
@RequiredArgsConstructor
public class TrackController {
    private final TrackService trackService;
    private final TrackAssignmentService trackAssignmentService;

    @Operation(summary = "트랙 상세 조회")
    @GetMapping("/tracks/{trackId}")
    public TrackResponse get(
            @PathVariable Long trackId,
            @UserId Long userId
            ) {

        return trackService.getTrackResponseById(trackId, userId);
    }

    @Operation(summary = "트랙 생성")
    @PostMapping("/groups/{gid}/tracks")
    public TrackResponse create(
            @PathVariable Long gid,
            @RequestBody TrackCreationRequest trackCreationRequest,
            @UserId Long userId
            ) {
        return trackService.create(gid, trackCreationRequest, userId);
    }

    @Operation(summary = "트랙 수정")
    @PutMapping("/tracks/{trackId}")
    public void update(
            @PathVariable Long trackId,
            @RequestBody TrackUpdateRequest updateRequest,
            @UserId Long userId
    ) {
        trackService.update(trackId, updateRequest, userId);
    }

    @Operation(summary = "그룹 내 트랙 검색", description = "특정 그룹에 속한 트랙들을 조회 및 검색합니다.")
    @GetMapping("/groups/{gid}/tracks")
    public Page<TrackResponse> searchTrackInGroup(
            @UserId Long userId,
            @PathVariable Long gid,
            @ModelAttribute TrackSearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        return trackService.searchTrackInGroup(gid, userId, searchRequest, pageable);
    }


    @Operation(summary = "사용자 트랙 목록 조회", description = "로그인한 사용자가 수강 중이거나 관련된 트랙 목록을 조회합니다.")
    @GetMapping("/users/me/tracks")
    public Page<TrackWithGroupResponse> searchTrackInUser(
            @UserId Long userId,
            @ModelAttribute TrackWithGroupSearchRequest searchRequest,
            @PageableDefault Pageable pageable
    ) {
        return trackService.searchTrackInGroupInUser(userId, searchRequest, pageable);
    }

    @Operation(summary = "트랙 수강 신청")
    @PostMapping("/tracks/{trackId}/assignments")
    public void apply(
            @PathVariable Long trackId,
            @UserId Long userId
    ) {
        trackAssignmentService.applyTrack(userId, trackId);
    }

    @Operation(summary = "트랙 수강 신청 취소")
    @DeleteMapping("/tracks/{trackId}/assignments")
    public void assignmentCancel(
            @PathVariable Long trackId,
            @UserId Long userId
    ) {
        trackAssignmentService.cancelTrack(trackId, userId);
    }

    @Operation(summary = "트랙 멤버 조회", description = "트랙을 신청한 멤버를 조회합니다.")
    @GetMapping(value = "/track/{trackId}/users")
    public List<TrackMemberResponse> findTrackMember(
            @PathVariable Long trackId,
            @RequestParam(defaultValue = "ALL") TrackRoleFilter trackRoleFilter,
            @UserId Long userId
    ) {
        return trackService.findUserProfileByTrackIdAndRole(trackId, trackRoleFilter, userId);
    }
}
