package com.kesi.tracker.group.application.mapper;

import com.kesi.tracker.file.application.dto.FileResponse;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.group.application.dto.*;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.user.application.dto.UserProfileResponse;

import java.time.LocalDateTime;
import java.util.List;

public class GroupMapper {
    public static GroupResponse toGroupResponse(Group group, UserProfileResponse leader, List<FileResponse> profileFiles) {
        return GroupResponse.builder()
                .name(group.getName())
                .introduction(group.getIntroduce())
                .description(group.getDescription())
                .leader(leader)
                .creationDate(group.getCreatedAt())
                .memberCount(group.getMemberCount())
                .profileFiles(profileFiles)
                .build();
    }

    public static GroupProfileResponse toGroupProfileResponse(
            Group group,
            UserProfileResponse leader,
            List<FileAccessUrl> profileImageUrls) {
        return GroupProfileResponse.builder()
                .gid(group.getGid())
                .name(group.getName())
                .introduction(group.getIntroduce())
                .leader(leader)
                .creationDate(group.getCreatedAt())
                .memberCount(group.getMemberCount())
                .profileImageUrls(profileImageUrls.stream().map(FileAccessUrl::value).toList())
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
                .memberCount(1) //초기 인원은 1명
                .build();
    }

    public static Group toGroup(
            Group originalGroup,
            GroupUpdateRequest updateRequest,
            Long modifyBy
    ) {
        return Group.builder()
                .gid(originalGroup.getGid())
                .name(updateRequest.getName())
                .description(updateRequest.getDescription())
                .introduce(updateRequest.getIntroduction())
                .access(updateRequest.getAccess())
                .createdBy(modifyBy)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MyGroupInfoResponse toGroupInfoResponse(GroupMember groupMember) {
        return MyGroupInfoResponse.builder()
                .role(groupMember.getRole())
                .trackRole(groupMember.getTrackRole())
                .build();
    }
}
