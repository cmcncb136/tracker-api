package com.kesi.tracker.group.application.dto;

import com.kesi.tracker.group.domain.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "그룹 생성 요청")
@Data
public class GroupCreationRequest {
    @Schema(description = "그룹 명", example = "스프링 스터디")
    private String name;
    @Schema(description = "그룹 한줄 소개", example = "스프링을 깊게 공부하는 모임입니다.")
    private String introduction;
    @Schema(description = "그룹 상세 설명", example = "매주 일요일 강남역에서 오프라인 스터디를 진행합니다.")
    private String description;
    @Schema(description = "공개 여부 (PUBLIC, PRIVATE)", example = "PUBLIC")
    private AccessType access;
    @Schema(description = "프로필 이미지 파일 ID 목록")
    private List<Long> profileFileIds;
}
