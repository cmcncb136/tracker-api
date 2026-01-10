package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.repository.FileRepository;
import com.kesi.tracker.file.application.storage.FileUrlAccessPolicy;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileUrlAccessPolicy fileUrlAccessPolicy;

    @Override
    public Optional<File> findById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public List<File> findByIds(List<Long> ids) {
        return fileRepository.findByIds(ids);
    }

    @Override
    public List<File> findByOwner(FileOwner owner) {
        return fileRepository.findByOwner(owner);
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public List<File> save(List<File> files) {
        return fileRepository.save(files);
    }

    @Override
    public void deleteById(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public List<FileAccessUrl> findAccessUrlByOwner(FileOwner owner) {
        return findByOwner(owner).stream()
                .map(File::getStorageKey)
                .map(fileUrlAccessPolicy::generate)
                .map(FileAccessUrl::new)
                .toList();
    }
}
