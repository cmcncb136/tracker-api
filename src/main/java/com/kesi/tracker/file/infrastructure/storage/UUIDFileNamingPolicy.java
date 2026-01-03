package com.kesi.tracker.file.infrastructure.storage;

import com.kesi.tracker.file.application.storage.FileNamingPolicy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class UUIDFileNamingPolicy implements FileNamingPolicy {
    @Override
    public String generate(MultipartFile file) {
        return UUID.randomUUID() + "." + file.getContentType();
    }
}
