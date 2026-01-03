package com.kesi.tracker.file.application.storage;

import com.kesi.tracker.file.domain.StorageKey;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    void upload(MultipartFile file, StorageKey key);
    void upload(List<MultipartFile> files, StorageKey key);
    void copy(StorageKey src, StorageKey dest);
}