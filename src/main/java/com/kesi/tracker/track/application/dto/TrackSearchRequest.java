package com.kesi.tracker.track.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kesi.tracker.track.application.query.TrackSearchCondition;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrackSearchRequest {
    private String text;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate assignmentStartAt;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate assignmentEndAt;

    public TrackSearchCondition toTrackSearchCondition() {
        return TrackSearchCondition.builder()
                .title(text)
                .introduction(text)
                .assignmentStartAt(assignmentStartAt.atTime(0, 0, 0))
                .assignmentEndAt(assignmentEndAt.atTime(23, 59, 59))
                .build();
    }
}
