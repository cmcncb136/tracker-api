package com.kesi.tracker.file.application;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.group.application.query.FileOwners;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FileService {
    Optional<File> findById(Long id);

    List<File> findByIds(List<Long> ids);

    List<File> findByOwner(FileOwner owner);

    File save(File file);

    List<File> save(List<File> files);

    void deleteById(Long id);

    List<FileAccessUrl> findAccessUrlByOwner(FileOwner owner);

    Map<Long, List<FileAccessUrl>> findAccessUrlByOwners(FileOwners owners);

    List<File> assignFileOwner(FileOwner owner, List<Long> fileIds);
}
