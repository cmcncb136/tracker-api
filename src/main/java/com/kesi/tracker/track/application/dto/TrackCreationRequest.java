package com.kesi.tracker.track.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "트랙 생성 요청")
@Data
public class TrackCreationRequest {
    @Schema(description = "그룹 ID", example = "1")
    private Long gid;

    @Schema(description = "수강 정원", example = "20")
    private int capacity;

    @Schema(description = "트랙 제목", example = "스프링 핵심 원리")
    private String title;
    @Schema(description = "트랙 상세 설명", example = "스프링 프레임워크의 핵심 기술을 학습합니다.")
    private String description;
    @Schema(description = "트랙 한줄 소개", example = "스프링의 정석")
    private String introduction;
    @Schema(description = "장소", example = "강남역 5번 출구 카페")
    private String place;
    @Schema(description = "참가비", example = "10000")
    private Long cost;

    @Schema(description = "수강 신청 시작 일시", example = "2026-02-01T00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime assignmentStartAt;
    @Schema(description = "수강 신청 종료 일시", example = "2026-02-28T23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime assignmentEndAt;
}
