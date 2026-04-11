package com.kesi.tracker.file.application;

import com.kesi.tracker.file.application.repository.FileRepository;
import com.kesi.tracker.file.application.storage.FileStorageService;
import com.kesi.tracker.file.application.storage.FileUrlAccessPolicy;
import com.kesi.tracker.file.application.storage.StorageKeyPolicy;
import com.kesi.tracker.file.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileUrlAccessPolicy fileUrlAccessPolicy;
    private final FileStorageService fileStorageService;
    private final StorageKeyPolicy storageKeyPolicy;

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
        List<File> files = fileRepository.findByOwners(fileOwers);

        Map<Long, List<FileAccessUrl>> fileMap = files.stream().collect(Collectors.groupingBy(
                file -> file.getOwner().ownerId(),
                Collectors.mapping(
                        file -> new FileAccessUrl(fileUrlAccessPolicy.generate(file.getStorageKey())),
                        Collectors.toList()
                )));

        return fileOwers.ownerIds().stream().collect(Collectors.toMap(
                Function.identity(),
                ownerId -> fileMap.getOrDefault(ownerId, Collections.emptyList())
        ));
    }

    @Override
    @Transactional
    public List<File> assignAsAttachment(FileOwner owner, List<Long> fileIds) {
        return this.assign(owner, FilePurpose.ATTACHMENT, fileIds);
    }

    @Override
    @Transactional
    public List<File> assignAsProfile(FileOwner owner, List<Long> fileIds) {
        return this.assign(owner, FilePurpose.PROFILE, fileIds);
    }

    @Override
    @Transactional
    public List<File> assign(FileOwner owner, FilePurpose purpose, List<Long> fileIds) {
        if(ObjectUtils.isEmpty(fileIds)) return List.of();

        List<File> files = this.findByIds(fileIds);

        for(File file : files){
            StorageKey newStorageKey = storageKeyPolicy.generate(owner, purpose, file.getMetadata().virtualName());
            fileStorageService.copy(file.getStorageKey(), newStorageKey);

            file.assign(owner, purpose);
            file.replace(newStorageKey);
        }

        return this.save(files);
    }

    @Override
    @Transactional
    public List<File> updateFromOwner(FileOwner owner, FilePurpose purpose, List<Long> fileIds) {
        Set<Long> fileIdSet = new HashSet<>(fileIds);
        if(ObjectUtils.isEmpty(fileIdSet)) return List.of();

        //기존 내용 조회
        List<File> originalFiles = fileRepository.findByOwnerAndPurpose(owner, purpose);
        Set<Long> originalFileIdSet = originalFiles.stream()
                .map(File::getId).collect(Collectors.toSet());

        //삭제할 파일 필터링 및 삭제
        List<Long> deletedFileIds = originalFileIdSet.stream()
                .filter(id -> !fileIdSet.contains(id))
                .toList();
        if(!deletedFileIds.isEmpty()) fileRepository.deleteByIds(deletedFileIds);

        //추가할 파일 필터링 및 owner 할당
        List<Long> addedFileIds = fileIdSet.stream()
                .filter(fileId -> !originalFileIdSet.contains(fileId))
                .toList();

        return Stream.concat(
                originalFiles.stream(),
                this.assignAsProfile(owner, addedFileIds).stream()
                ).toList();
    }
}
