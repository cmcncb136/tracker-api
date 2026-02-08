package com.kesi.tracker.notice.infrastructure;

import com.kesi.tracker.notice.application.repository.NoticeRepository;
import com.kesi.tracker.notice.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {
    private final NoticeJpaRepository jpaRepository;
    @Override
    public Notice create(Notice notice) {
        return jpaRepository.save(NoticeEntity.fromDomain(notice)).toDomain();
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return jpaRepository.findById(id).map(NoticeEntity::toDomain);
    }

    @Override
    public Notice update(Notice notice) {
        return jpaRepository.save(NoticeEntity.fromDomain(notice)).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<Notice> findByGidAndTitleContainingIgnoreCase(Long gid, String title, Pageable pageable) {
        return jpaRepository.findByGidAndTitleContainingIgnoreCase(gid, title, pageable)
                .map(NoticeEntity::toDomain);
    }
}
