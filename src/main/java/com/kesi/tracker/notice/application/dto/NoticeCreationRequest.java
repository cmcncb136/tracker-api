package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.notice.domain.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "공지사항 생성 요청")
@Data
public class NoticeCreationRequest {
    @Schema(description = "공지 유형 (NORMAL, IMPORTANT)", example = "IMPORTANT")
    private NoticeType type;
    @Schema(description = "공지 제목", example = "오프라인 스터디 장소 변경 안내")
    private String title;
    @Schema(description = "공지 내용", example = "스터디 장소가 강남역에서 잠실역으로 변경되었습니다.")
    private String content;
    @Schema(description = "첨부 파일 ID 목록")
    private List<Long> attachmentFileIds;
}
