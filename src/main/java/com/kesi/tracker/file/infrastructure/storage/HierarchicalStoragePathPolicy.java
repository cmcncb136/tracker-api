package com.kesi.tracker.file.infrastructure.storage;

import com.kesi.tracker.file.application.storage.StorageKeyPolicy;
import com.kesi.tracker.file.domain.*;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Component
public class HierarchicalStoragePathPolicy implements StorageKeyPolicy {
    public static final String TEMP_PATH = "temp/";

    @Override
    public StorageKey resolveAttached(File file) {
        FileOwner owner = file.getOwner();
        FilePurpose purpose = file.getPurpose();
        if(ObjectUtils.isEmpty(owner) || ObjectUtils.isEmpty(purpose))
            throw new IllegalArgumentException("The file has no owner or purpose");

        OwnerType ownerType = owner.ownerType();
        String directory = getPath(owner, ownerType, purpose);

        return new StorageKey(directory + "/" + file.getMetadata().virtualName());
    }

    @NotNull
    private static String getPath(FileOwner owner, OwnerType ownerType, FilePurpose purpose) {
        Long ownerId = owner.ownerId();

        String directory = switch (ownerType) {
            case USER -> joinWithSlash("users", ownerId.toString(), purpose.getDirName());
            case GROUP -> joinWithSlash( "groups", ownerId.toString(), purpose.getDirName());
            case TRACK -> joinWithSlash( "tracks", ownerId.toString(), purpose.getDirName());
            case NOTICE -> joinWithSlash( "notices", ownerId.toString(), purpose.getDirName());
            case GLOBAL_NOTICE -> joinWithSlash( "global", "notices", ownerId.toString(), purpose.getDirName());
        };
        return directory;
    }

    @Override
    public StorageKey resolveTemp(String fileName) {
        return new StorageKey(joinWithSlash(TEMP_PATH, fileName));
    }

    private static String joinWithSlash(String... elements) {
        if (elements == null || elements.length == 0) {
            return "";
        }
        return String.join("/", elements);
    }

}
