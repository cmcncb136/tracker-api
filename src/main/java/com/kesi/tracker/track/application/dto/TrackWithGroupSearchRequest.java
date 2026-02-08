package com.kesi.tracker.track.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kesi.tracker.track.application.query.TrackWithGroupSearchCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Schema(description = "그룹 포함 트랙 검색 요청 DTO")
@Data
public class TrackWithGroupSearchRequest {
    @Schema(description = "검색어 (제목, 소개, 그룹명)", example = "스프링")
    private String text;

    @Schema(description = "수강 시작일 검색 범위", example = "2026-02-01")
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate assignmentStartAt;

    @Schema(description = "수강 종료일 검색 범위", example = "2026-02-28")
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate assignmentEndAt;

    public TrackWithGroupSearchCondition toTrackWithGroupSearchCondition() {
        return TrackWithGroupSearchCondition.builder()
                .title(text)
                .introduction(text)
                .groupName(text)
                .assignmentStartAt(assignmentStartAt.atTime(0, 0, 0))
                .assignmentEndAt(assignmentEndAt.atTime(23, 59, 59))
                .build();
    }
}
