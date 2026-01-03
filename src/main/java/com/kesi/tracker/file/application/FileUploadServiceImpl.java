package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.dto.FileUploadResponse;
import com.kesi.tracker.file.application.mapper.FileMapper;
import com.kesi.tracker.file.application.repository.FileRepository;
import com.kesi.tracker.file.application.storage.FileStorageService;
import com.kesi.tracker.file.application.storage.FileNamingPolicy;
import com.kesi.tracker.file.application.storage.StorageKeyPolicy;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileMetadata;
import com.kesi.tracker.file.domain.StorageKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final StorageKeyPolicy storagePathPolicy;
    private final FileNamingPolicy fileNamingPolicy;

    @Override
    public FileUploadResponse uploadToTemp(MultipartFile file) {
        String fileName = fileNamingPolicy.generate(file);
        StorageKey key = storagePathPolicy.resolveTemp(fileName);

        fileStorageService.upload(file, key);
        FileMetadata fileMetadata = FileMapper.toFileMetadata(file, fileName);

        File savedFile = fileRepository.save(File.builder()
                .storageKey(key)
                .metadata(fileMetadata)
                .build());

        return FileMapper.toFileUploadResponse(savedFile);
    }

    @Override //Todo. 필요시 성능 개선
    public List<FileUploadResponse> uploadToTemp(List<MultipartFile> files) {
        return files.stream().map(this::uploadToTemp).collect(Collectors.toList());
    }
}
