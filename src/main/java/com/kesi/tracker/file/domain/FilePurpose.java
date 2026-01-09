package com.kesi.tracker.file.domain;

import lombok.Getter;

public enum FilePurpose {
    PROFILE("profile") {
        @Override
        public boolean validate(FileMetadata fileMetadata) {
            return isImage(fileMetadata);
        }
    },

    CONTENT("content") {
        @Override
        public boolean validate(FileMetadata fileMetadata) {
            return isImage(fileMetadata);
        }
    },

    ATTACHMENT("attachment") {
        @Override
        public boolean validate(FileMetadata fileMetadata) {
            return true;
        }
    };

    @Getter
    private final String dirName;

    FilePurpose(String dirName) {
        this.dirName = dirName;
    }
    public abstract boolean validate(FileMetadata fileMetadata);
    protected boolean isImage(FileMetadata fileMetadata) {
        return fileMetadata.contentType().startsWith("image");
    }
}