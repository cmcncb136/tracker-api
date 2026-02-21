package com.kesi.tracker.notice.infrastructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {
    Page<NoticeEntity> findByGidAndTitleContainingIgnoreCase(Long gid, String title, Pageable pageable);
    Page<NoticeEntity> findByGid(Long gid, Pageable pageable);
}
