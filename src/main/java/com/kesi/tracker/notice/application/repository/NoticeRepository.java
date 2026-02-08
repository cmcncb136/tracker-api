package com.kesi.tracker.notice.application.repository;

import com.kesi.tracker.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NoticeRepository {
    Notice create(Notice notice);
    Optional<Notice> findById(Long id);
    Notice update(Notice notice);
    void deleteById(Long id);
    Page<Notice> findByGidAndTitleContainingIgnoreCase(Long gid, String title, Pageable pageable);
}
