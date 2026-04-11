package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.dto.FileResponse;
import com.kesi.tracker.file.domain.*;

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

    List<FileResponse> findResponseByOwner(FileOwner owner);

    List<FileAccessUrl> findAccessUrlByOwner(FileOwner owner);
    Map<Long, List<FileAccessUrl>> findAccessUrlByOwners(FileOwners owners);

    List<File> assignAsAttachment(FileOwner owner, List<Long> fileIds);
    List<File> assignAsProfile(FileOwner owner, List<Long> fileIds);
    List<File> assign(FileOwner owner, FilePurpose purpose, List<Long> fileIds);
    List<File> updateFromOwner(FileOwner owner, FilePurpose purpose, List<Long> fileIds);
}
