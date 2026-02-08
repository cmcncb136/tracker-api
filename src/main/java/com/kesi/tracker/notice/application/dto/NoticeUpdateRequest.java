package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.notice.domain.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "공지사항 수정 요청")
@Data
public class NoticeUpdateRequest {
    @Schema(description = "공지 ID", example = "1")
    private Long id;
    @Schema(description = "공지 유형", example = "NOTICE")
    private NoticeType type;
    @Schema(description = "공지 제목", example = "[수정] 오프라인 스터디 장소 변경")
    private String title;
    @Schema(description = "공지 내용", example = "변경된 장소는 잠실역 2번 출구 앞입니다.")
    private String content;
    @Schema(description = "첨부 파일 ID 목록")
    private List<Long> profileFileIds;
}
