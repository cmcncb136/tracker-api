package com.kesi.tracker.track.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.kesi.tracker.user.application.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "트랙 상세 응답")
@Data
@Builder
public class TrackResponse {
    @Schema(description = "트랙 ID")
    private Long id;
    @Schema(description = "그룹 ID")
    private Long gid;
    @Schema(description = "호스트 프로필")
    private UserProfileResponse hostProfile;
    @Schema(description = "수강 정원")
    private int capacity;
    @Schema(description = "현재 수강 인원")
    private int followersCount;
    @Schema(description = "트랙 제목")
    private String title;
    @Schema(description = "트랙 한줄 소개")
    private String introduction;
    @Schema(description = "트랙 상세 설명")
    private String description;
    @Schema(description = "장소")
    private String place;
    @Schema(description = "참가비")
    private Long cost;
    @Schema(description = "수강 신청 시작 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime assignmentStartAt;
    @Schema(description = "수강 신청 종료 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime assignmentEndAt;
    @Schema(description = "생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @Schema(description = "수정 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @Schema(description = "트랙 관련 이미지 URL 목록")
    private List<String> profileUrls;
}
