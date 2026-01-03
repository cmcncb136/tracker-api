package com.kesi.tracker.file.application.storage;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.StorageKey;


public interface StorageKeyPolicy {
    StorageKey resolveAttached(File file);

    StorageKey resolveTemp(String fileName);
}
