package com.kesi.tracker.file.application;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;

import java.util.List;
import java.util.Optional;

public interface FileService {
    Optional<File> findById(Long id);
    List<File> findByIds(List<Long> ids);
    List<File> findByOwner(FileOwner owner);
    File save(File file);
    List<File> save(List<File> files);
    void deleteById(Long id);
}
