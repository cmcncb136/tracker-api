package com.kesi.tracker.file.application.repository;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.OwnerType;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    File save(File file);
    List<File> save(List<File> files);
    Optional<File> findById(Long id);
    List<File> findByIds(List<Long> ids);
    void deleteById(Long id);
    List<File> findByOwner(FileOwner owner);
}
