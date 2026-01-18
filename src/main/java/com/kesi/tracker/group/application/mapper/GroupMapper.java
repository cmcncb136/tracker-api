package com.kesi.tracker.group.application.mapper;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.group.application.dto.GroupCreationRequest;
import com.kesi.tracker.group.application.dto.GroupProfileResponse;
import com.kesi.tracker.group.application.dto.GroupResponse;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.user.application.dto.UserProfileResponse;

import java.time.LocalDateTime;
import java.util.List;

public class GroupMapper {
    public static GroupResponse toGroupResponse(Group group, UserProfileResponse creator, List<FileAccessUrl> profileImageUrls) {
        return GroupResponse.builder()
                .name(group.getName())
                .introduction(group.getIntroduce())
                .description(group.getDescription())
                .creator(creator)
                .creationDate(group.getCreatedAt())
                .memberCount(group.getMemberCount())
                .profileImageUrls(profileImageUrls.stream().map(FileAccessUrl::toString).toList())
                .build();
    }

    public static GroupProfileResponse toGroupProfileResponse(Group group, List<FileAccessUrl> profileImageUrls) {
        return GroupProfileResponse.builder()
                .name(group.getName())
                .introduction(group.getIntroduce())
                .creationDate(group.getCreatedAt())
                .memberCount(group.getMemberCount())
                .profileImageUrls(profileImageUrls.stream().map(FileAccessUrl::toString).toList())
                .build();
    }

    public static Group toGroup(GroupCreationRequest groupCreationRequest, Long createBy) {
        return Group.builder()
                .name(groupCreationRequest.getName())
                .description(groupCreationRequest.getDescription())
                .introduce(groupCreationRequest.getIntroduction())
                .access(groupCreationRequest.getAccess())
                .createdBy(createBy)
                .createdAt(LocalDateTime.now())
                .memberCount(1)
                .build();
    }
}
