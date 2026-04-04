package com.kesi.tracker.track.application.mapper;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import com.kesi.tracker.track.application.dto.TrackCreationRequest;
import com.kesi.tracker.track.application.dto.TrackResponse;
import com.kesi.tracker.track.application.dto.TrackUpdateRequest;
import com.kesi.tracker.track.application.dto.TrackWithGroupResponse;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.user.application.dto.UserProfileResponse;

import java.time.LocalDateTime;
import java.util.List;

public class TrackMapper {
    public static TrackResponse toTrackResponse(
            Track track,
            UserProfileResponse hostProfile,
            List<FileAccessUrl> profileUrls) {

        return TrackResponse.builder()
                .id(track.getId())
                .gid(track.getGid())
                .hostProfile(hostProfile)
                .capacity(track.getCapacity())
                .followersCount(track.getFollowerCnt())

                .title(track.getTitle())
                .introduction(track.getIntroduce())
                .description(track.getDescription())
                .place(track.getPlace())
                .cost(track.getCost())

                .assignmentStartAt(track.getAssignmentStartAt())
                .assignmentEndAt(track.getAssignmentEndAt())

                .createdAt(track.getCreatedAt())
                .modifiedAt(track.getModifiedAt())
                .profileUrls(profileUrls.stream().map(FileAccessUrl::value).toList())
                .build();
    }

    public static TrackWithGroupResponse toTrackWithGroupResponse(
            TrackResponse track,
            GroupProfileResponse group
    ) {
        return TrackWithGroupResponse.builder()
                .track(track)
                .group(group)
                .build();
    }

    public static Track toTrack(
            Long gid,
            Track originalTrack,
            TrackUpdateRequest updateRequest,
            Long currentUserId
    ) {
        return Track.builder()
                .id(originalTrack.getId())
                .gid(gid)
                .hostId(originalTrack.getHostId())
                .capacity(updateRequest.getCapacity())
                .followerCnt(originalTrack.getFollowerCnt())
                .title(updateRequest.getTitle())
                .introduce(updateRequest.getIntroduction())
                .description(updateRequest.getDescription())
                .place(updateRequest.getPlace())
                .cost(updateRequest.getCost())
                .assignmentStartAt(updateRequest.getAssignmentStartAt())
                .assignmentEndAt(updateRequest.getAssignmentEndAt())
                .createdAt(originalTrack.getCreatedAt())
                .modifiedAt(LocalDateTime.now())
                .createdBy(originalTrack.getCreatedBy())
                .modifiedBy(currentUserId)
                .build();
    }

    public static Track toTrack(
            Long gid,
            TrackCreationRequest trackCreationRequest,
            Long currentUserId) {
        return Track.builder()
                .gid(gid)
                .hostId(currentUserId)
                .capacity(trackCreationRequest.getCapacity())
                .followerCnt(0)
                .title(trackCreationRequest.getTitle())
                .introduce(trackCreationRequest.getIntroduction())
                .description(trackCreationRequest.getDescription())
                .place(trackCreationRequest.getPlace())
                .cost(trackCreationRequest.getCost())
                .assignmentStartAt(trackCreationRequest.getAssignmentStartAt())
                .assignmentEndAt(trackCreationRequest.getAssignmentEndAt())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .createdBy(currentUserId)
                .modifiedBy(currentUserId)
                .build();
    }
}
