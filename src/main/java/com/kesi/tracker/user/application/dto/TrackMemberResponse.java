package com.kesi.tracker.user.application.dto;

import com.kesi.tracker.track.domain.TrackRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrackMemberResponse {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "트랙 관계 id(TrackMember id)")
    private Long relationId;
    @Schema(description = "트랙 역할")
    private TrackRole role;
    @Schema(description = "프로필 이미지 URL 목록")
    private List<String> profileImageUrls;
}
