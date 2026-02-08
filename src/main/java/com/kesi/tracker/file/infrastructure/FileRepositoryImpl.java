package com.kesi.tracker.file.infrastructure;

import com.kesi.tracker.file.application.repository.FileRepository;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.FileOwners;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    private final FileJpaRepository fileJpaRepository;

    @Override
    public File save(File file) {
        return fileJpaRepository.save(FileEntity.fromDomain(file)).toDomain();
    }

    @Override
    public List<File> save(List<File> files) {
        return fileJpaRepository.saveAll(files.stream().map(FileEntity::fromDomain).toList())
                .stream().map(FileEntity::toDomain).toList();
    }

    @Override
    public Optional<File> findById(Long id) {
        return fileJpaRepository.findById(id).map(FileEntity::toDomain);
    }

    @Override
    public List<File> findByIds(List<Long> ids) {
        return fileJpaRepository.findByIdIn(ids).stream().map(FileEntity::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        fileJpaRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        fileJpaRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public List<File> findByOwner(FileOwner owner) {
        return fileJpaRepository.findByOwnerIdAndOwnerType(owner.ownerId(), owner.ownerType())
                .stream().map(FileEntity::toDomain).toList();
    }

    @Override
    public List<File> findByOwners(FileOwners owners) {
        return fileJpaRepository.findByOwnerTypeAndOwnerIdIn(owners.ownerType(), owners.ownerIds())
                .stream().map(FileEntity::toDomain).toList();
    }

}
