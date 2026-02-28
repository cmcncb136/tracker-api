package com.kesi.tracker.user.application.dto;

import com.kesi.tracker.group.domain.GroupRole;
import com.kesi.tracker.group.domain.GroupTrackRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "그룹 멤버(유저) 프로필 응답")
@Data
@Builder
public class GroupMemberProfileResponse {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "그룹 역할")
    private GroupRole role;
    @Schema(description = "트랙 역할 (HOST, FOLLOWER)")
    private GroupTrackRole trackRole;
    @Schema(description = "프로필 이미지 URL 목록")
    private List<String> profileImageUrls;
}
