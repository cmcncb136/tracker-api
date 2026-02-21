package com.kesi.tracker.group.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "그룹 요약 프로필 응답")
@Data
@Builder
public class GroupProfileResponse {
    @Schema(description = "그룹 ID")
    private Long gid;
    @Schema(description = "그룹 명")
    private String name;
    @Schema(description = "그룹 소개")
    private String introduction;
    @Schema(description = "그룹 생성자 프로필")
    private UserProfileResponse creator;
    @Schema(description = "그룹 생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime creationDate;
    @Schema(description = "그룹 멤버 수")
    private Integer memberCount;
    @Schema(description = "그룹 프로필 이미지 URL 목록")
    private List<String> profileImageUrls;
}
