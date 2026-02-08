package com.kesi.tracker.notice.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kesi.tracker.notice.domain.NoticeType;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "공지사항 상세 응답")
@Data
@Builder
public class NoticeResponse {
    @Schema(description = "공지 ID")
    private Long id;
    @Schema(description = "그룹 ID")
    private Long gid;
    @Schema(description = "공지 유형")
    private NoticeType type;
    @Schema(description = "작성자 프로필")
    private UserProfileResponse authorProfile;
    @Schema(description = "공지 제목")
    private String title;
    @Schema(description = "공지 내용")
    private String content;
    @Schema(description = "생성 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @Schema(description = "마지막 수정 일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @Schema(description = "최종 수정자 프로필")
    private UserProfileResponse modifiedAuthorProfile;
    @Schema(description = "첨부 파일 URL 목록")
    private List<String> attachmentFileUrls;
}
