package com.kesi.tracker.file.infrastructure;


import com.kesi.tracker.file.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", length = 30)
    private OwnerType ownerType;

    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", length = 30)
    private FilePurpose purpose;

    @Column(name = "storage_key", nullable = false, unique = true)
    private String storageKey;

    @Embedded
    private FileMetadataEmbeddable metadata;

    public File toDomain() {
        return File.builder()
                .id(id)
                .metadata(metadata.toDomain())
                .owner(
                        FileOwner.builder()
                                .ownerType(ownerType)
                                .ownerId(ownerId)
                                .build())
                .storageKey(new StorageKey(storageKey))
                .purpose(purpose)
                .build();
    }

    public static FileEntity fromDomain(File file) {
        return FileEntity.builder()
                .id(file.getId())
                .metadata(FileMetadataEmbeddable.fromDomain(file.getMetadata()))
                .ownerType(file.getOwner() != null ? file.getOwner().getOwnerType() : null)
                .ownerId(file.getOwner() != null ? file.getOwner().getOwnerId() : null)
                .purpose(file.getPurpose())
                .storageKey(file.getStorageKey().value())
                .build();
    }
}





