package com.kesi.tracker.file.infrastructure;

import com.kesi.tracker.file.domain.FilePurpose;
import com.kesi.tracker.file.domain.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwnerIdAndOwnerType(Long ownerId, OwnerType ownerType);
    List<FileEntity> findByIdIn(List<Long> ids);
    List<FileEntity> findByOwnerTypeAndOwnerIdIn(OwnerType ownerType, List<Long> ids);
    List<FileEntity> findByOwnerTypeAndPurposeAndOwnerId(OwnerType ownerType, FilePurpose purpose, Long id);
    List<FileEntity> findByOwnerTypeAndPurposeAndOwnerIdIn(OwnerType ownerType, FilePurpose purpose, List<Long> ids);
}
