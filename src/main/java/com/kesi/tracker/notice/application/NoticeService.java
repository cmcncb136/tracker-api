package com.kesi.tracker.notice.application;

import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import com.kesi.tracker.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    Notice create(Notice notice);
    Notice create(NoticeCreationRequest request, Long currentUid);

    Notice update(Notice notice);
    Notice update(NoticeUpdateRequest request, Long currentUid);

    NoticeResponse getById(Long id, Long currentUid);
    Page<NoticeTitleResponse> findByGidAndNameContainingIgnoreCase(Long currentUid, List<String> title, Pageable pageable);
}
