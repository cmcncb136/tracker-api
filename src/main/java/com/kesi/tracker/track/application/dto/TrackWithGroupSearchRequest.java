package com.kesi.tracker.track.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kesi.tracker.track.application.query.TrackWithGroupSearchCondition;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrackWithGroupSearchRequest {
    private String text;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate assignmentStartAt;

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
