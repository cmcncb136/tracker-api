package com.kesi.tracker.file.application.storage;

import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.FilePurpose;
import com.kesi.tracker.file.domain.StorageKey;


public interface StorageKeyPolicy {
    StorageKey generate(FileOwner owner, FilePurpose purpose, String virtualName);

    StorageKey resolveTemp(String fileName);
}
