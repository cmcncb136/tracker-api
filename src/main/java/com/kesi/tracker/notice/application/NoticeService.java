package com.kesi.tracker.notice.application;

import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import com.kesi.tracker.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    Notice create(Notice notice);
    void create(NoticeCreationRequest request, Long currentUid);

    Notice update(Notice notice);
    Notice update(NoticeUpdateRequest request, Long currentUid);

    Notice getById(Long noticeId);
    NoticeResponse getById(Long id, Long currentUid);
    Page<NoticeTitleResponse> search(Long gid, String keyword, Pageable pageable);
}
