package com.kesi.tracker.file.domain;

import lombok.Getter;

public enum FilePurpose {
    PROFILE("profile"),
    CONTENT("content"),
    ATTACHMENT("attachment");

    @Getter
    private final String dirName;

    FilePurpose(String dirName) {
        this.dirName = dirName;
    }
    public boolean validate(FileMetadata fileMetadata) {
        return true; //Todo. 여기서 부터 시작
    }
}