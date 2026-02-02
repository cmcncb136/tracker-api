package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.repository.FileRepository;
import com.kesi.tracker.file.application.storage.FileUrlAccessPolicy;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.FileOwners;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Map<Long, List<FileAccessUrl>> findAccessUrlByOwners(FileOwners fileOwers) {
        List<File> files = fileRepository.findbyOwners(fileOwers);

        return files.stream().collect(Collectors.groupingBy(
                File::getId,
                Collectors.mapping(
                        file -> new FileAccessUrl(fileUrlAccessPolicy.generate(file.getStorageKey())),
                        Collectors.toList()
                )));
    }

    @Override
    public List<File> assignFileOwner(FileOwner owner, List<Long> fileIds) {
        if(ObjectUtils.isEmpty(fileIds)) return List.of();

        List<File> files = this.findByIds(fileIds);

        for(File file : files){
            file.assignAsProfile(owner);
        }

        return this.save(files);
    }
}
