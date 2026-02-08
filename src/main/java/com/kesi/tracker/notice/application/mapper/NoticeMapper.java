package com.kesi.tracker.notice.application.mapper;

import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import com.kesi.tracker.notice.domain.Notice;

import java.time.LocalDateTime;

public class NoticeMapper {
    public static Notice toNotice(NoticeCreationRequest request, Long currentUid) {
        return Notice.builder()
                .gid(request.getGid())
                .authorId(currentUid)
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .createdBy(currentUid)
                .modifiedBy(currentUid)
                .build();
    }

    public static Notice toNotice(NoticeUpdateRequest request, Notice original, Long currentUid) {
        return Notice.builder()
                .id(original.getId())
                .gid(original.getGid())
                .authorId(original.getAuthorId())
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(original.getCreatedAt())
                .modifiedAt(LocalDateTime.now())
                .createdBy(original.getCreatedBy())
                .modifiedBy(currentUid)
                .build();
    }
}
