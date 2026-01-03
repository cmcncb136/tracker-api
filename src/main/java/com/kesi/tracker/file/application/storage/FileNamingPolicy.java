package com.kesi.tracker.file.application.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileNamingPolicy {
    String generate(MultipartFile file);
}
