package com.kesi.tracker.file.application.presentation;

import com.kesi.tracker.file.application.FileUploadService;
import com.kesi.tracker.file.application.dto.FileUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File API", description = "파일 업로드 및 관리")
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileUploadService fileUploadService;

    @Operation(summary = "파일 다중 업로드", description = "여러 개의 파일을 임시 스토리지에 업로드하고 정보(ID, Key)를 반환받습니다.")
    @PostMapping("files")
    public List<FileUploadResponse> uploadFiles(
            @RequestParam List<MultipartFile> files
    ) {
        return fileUploadService.uploadToTemp(files);
    }
}
