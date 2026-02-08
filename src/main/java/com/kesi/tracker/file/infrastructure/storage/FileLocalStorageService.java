package com.kesi.tracker.file.infrastructure.storage;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.file.application.storage.FileStorageService;
import com.kesi.tracker.file.domain.StorageKey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileLocalStorageService implements FileStorageService {
    private static final String ROOT_PATH = "/etc/myapp/mingle/"; //Todo. properties로 분리

    @Override
    public void upload(MultipartFile file, StorageKey key) {
        Path path = Path.of(ROOT_PATH, key.value());

        File tempFile = new File(path.toFile().getAbsolutePath());

        Path parentDir = path.getParent();

        try {
            if(ObjectUtils.isNotEmpty(parentDir) && !Files.exists(path))
                Files.createDirectories(parentDir);

            file.transferTo(tempFile);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void upload(List<MultipartFile> files, StorageKey key) {
        for(MultipartFile file : files)
            upload(file, key);
    }

    @Override
    public void copy(StorageKey srcKey, StorageKey destKey) {
        Path src = Path.of(ROOT_PATH, srcKey.value());
        Path dest = Path.of(ROOT_PATH, destKey.value());

        if(!Files.exists(dest)) throw new BusinessException(ErrorCode.FILE_PATH_NOT_FOUND);

        Path srcParentDir = src.getParent();
        try {
            if(ObjectUtils.isNotEmpty(srcParentDir) && !Files.exists(srcParentDir))
                Files.createDirectories(srcParentDir);


            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}