package com.kesi.tracker.file.infrastructure.storage;

import com.kesi.tracker.file.application.storage.FileUrlAccessPolicy;
import com.kesi.tracker.file.domain.StorageKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultFileUrlAccessPolicy implements FileUrlAccessPolicy {
    @Value("${file.base-url}")
    private String baseUrl;

    @Override
    public String generate(StorageKey storageKey) {
        return baseUrl + storageKey.value();
    }
}
