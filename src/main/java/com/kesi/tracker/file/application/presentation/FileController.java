package com.kesi.tracker.file.application.presentation;

import com.kesi.tracker.file.application.FileUploadService;
import com.kesi.tracker.file.application.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileUploadService fileUploadService;

    @PostMapping("files")
    public List<FileUploadResponse> uploadFiles(
            @RequestParam List<MultipartFile> files
    ) {
        return fileUploadService.uploadToTemp(files);
    }
}
