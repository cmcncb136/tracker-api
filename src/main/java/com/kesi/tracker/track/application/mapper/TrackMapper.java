package com.kesi.tracker.track.application.mapper;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.track.application.dto.TrackResponse;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.user.application.dto.UserProfileResponse;

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

                .createdAt(track.getCreatedAt())
                .modifiedAt(track.getModifiedAt())
                .profileUrls(profileUrls.stream().map(FileAccessUrl::toString).toList())
                .build();
    }
}
