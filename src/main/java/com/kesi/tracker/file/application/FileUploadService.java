package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse uploadToTemp(MultipartFile file);
    List<FileUploadResponse> uploadToTemp(List<MultipartFile> files);
}
