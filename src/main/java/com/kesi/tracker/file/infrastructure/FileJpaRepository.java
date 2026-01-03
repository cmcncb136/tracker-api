package com.kesi.tracker.file.infrastructure;

import com.kesi.tracker.file.domain.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByOwnerIdAndOwnerType(Long ownerId, OwnerType ownerType);
}
