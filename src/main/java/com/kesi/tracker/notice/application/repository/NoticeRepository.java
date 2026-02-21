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

    //추후 필요시 QueryDSL로 변경
    Page<Notice> findByGidAndTitleContainingIgnoreCase(Long gid, String title, Pageable pageable);
    Page<Notice> findByGid(Long gid, Pageable pageable);
}
