package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.notice.domain.NoticeType;
import lombok.Data;

import java.util.List;

@Data
public class NoticeUpdateRequest {
    private Long id;
    private NoticeType type;
    private String title;
    private String content;
    private List<Long> profileFileIds;
}
