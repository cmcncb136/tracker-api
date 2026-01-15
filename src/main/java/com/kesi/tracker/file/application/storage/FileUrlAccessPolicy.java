package com.kesi.tracker.file.application.storage;

import com.kesi.tracker.file.domain.StorageKey;

public interface FileUrlAccessPolicy {
    String generate(StorageKey storageKey);
}
