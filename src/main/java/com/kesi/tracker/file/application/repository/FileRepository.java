package com.kesi.tracker.file.application.repository;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.OwnerType;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    File save(File file);
    Optional<File> findById(Long id);
    void deleteById(Long id);
    List<File> findByOwner(FileOwner owner);
}
