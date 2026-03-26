package com.kesi.tracker.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "그룹 초대를 요청한 사용자 프로필 응답")
@Data
@Builder
public class GroupJoinRequestUserProfileResponse {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "그룹 관계 id(GroupMember id)")
    private Long relationId;
    @Schema(description = "프로필 이미지 URL 목록")
    private List<String> profileImageUrls;
}
